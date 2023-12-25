package com.wuhobin.springbootgateway.filter;

import cn.dev33.satoken.stp.StpUtil;
import com.wuhobin.common.api.CommonResult;
import com.wuhobin.springbootgateway.utils.MonoUtils;
import com.wuhobin.springbootgateway.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * 全局过滤器 不需要配置 直接生效
 * 实现了GlobalFilter接口的filter 全局生效，不需要在配置中配置
 * @author wuhongbin
 * @version 1.0.0
 * @date 2023/12/21 18:05
 * @description
 */
@Configuration
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (Arrays.asList(securityProperties.getHttpUrls()).contains(path)){
            log.info("当前请求地址是白名单地址，直接放行： {}", path);
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst(StpUtil.getTokenName());
        if (StringUtils.isEmpty(token)){
            log.info("请求地址： {}, 未登录，请先登录", path);
            return MonoUtils.response(exchange.getResponse(), CommonResult.failed("未登录，请先登录"));
        }
        String userId = (String) StpUtil.getLoginIdByToken(token);
        if (StringUtils.isEmpty(userId)){
            log.info("请求地址： {}, token令牌解析错误，token={}", path, token);
            return MonoUtils.response(exchange.getResponse(), CommonResult.failed("token令牌解析错误"));
        }
        log.info("请求地址： {}, 当前登录用户userId={}", path, userId);
        ServerHttpRequest serverHttpRequest = request.mutate().header("userId", userId).build();
        exchange = exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }


}
