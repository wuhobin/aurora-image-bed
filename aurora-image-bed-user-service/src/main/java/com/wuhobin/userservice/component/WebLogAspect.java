package com.wuhobin.userservice.component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wuhobin.common.entity.log.WebLog;
import com.wuhobin.common.util.http.WebUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 统一日志处理切面
 * @author wuhongbin
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class WebLogAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestLog() {
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getLog() {
    }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postLog() {
    }


    @Before("requestLog() || getLog() || postLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "requestLog() || getLog() || postLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around("requestLog() || getLog() || postLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object result = joinPoint.proceed();
        try {
            //生成web日志，不影响请求结果
            generatorWebLog(joinPoint, startTime, request, result);
        }catch (Exception e){
            log.error("web请求日志异常",e);
        }
        return result;
    }

    private void generatorWebLog(ProceedingJoinPoint joinPoint, long startTime, HttpServletRequest request, Object result) {
        //记录请求信息(通过LogStash传入Elasticsearch)
        WebLog webLog = new WebLog();
        long endTime = System.currentTimeMillis();
        String urlStr = request.getRequestURL().toString();
        webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
        webLog.setIp(WebUtils.getClientRealIp(request));
        webLog.setMethod(request.getMethod());
        webLog.setParameter(getParameter(joinPoint));
        webLog.setResult(result);
        webLog.setSpendTime((int) (endTime - startTime));
        webLog.setStartTime(startTime);
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(request.getRequestURL().toString());
        //webLog.setUsername((String) request.getAttribute(SessionConstant.SESSION_APP_USER));
        webLog.setDescription(getDescription(joinPoint));
        //request.removeAttribute(SessionConstant.SESSION_APP_USER);
        log.info("web请求日志:{}", JSONUtil.toJsonStr(webLog));
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(ProceedingJoinPoint joinPoint) {
        //获取方法签名
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();
        //获取参数名称
        String[] parameterNames = signature.getParameterNames();
        //获取所有参数
        Object[] args = joinPoint.getArgs();
        //请求参数封装
        JSONObject jsonObject = new JSONObject();
        if(parameterNames !=null && parameterNames.length >0){
            for(int i=0; i<parameterNames.length;i++){
                jsonObject.put(parameterNames[i],args[i]);
            }
        }
        return jsonObject;
    }

    /**
     * 获取方法描述
     */
    private String getDescription(ProceedingJoinPoint joinPoint) {
        //获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法
        Method method = signature.getMethod();
        //获取注解对象
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        if (Objects.isNull(annotation)) {
            return "";
        }
        return annotation.value();
    }

}
