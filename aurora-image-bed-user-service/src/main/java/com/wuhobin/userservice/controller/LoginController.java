package com.wuhobin.userservice.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.wuhobin.common.api.CommonResult;
import com.wuhobin.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录认证
 * @author wuhongbin
 * @version 1.0.0
 * @date 2023/12/22 16:44
 * @description
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {

    @PostMapping("login")
    public CommonResult login(String mobile, String code){
        if (StringUtils.isAnyBlank(mobile, code)){
            return CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }
        Map<String, Object> map = new HashMap<>();
        StpUtil.login(mobile);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        map.put("tokenInfo", tokenInfo);
        return CommonResult.success(map);
    }

    @PostMapping("logout")
    public CommonResult logOut(@RequestHeader String userId){
        try {
            log.info("当前登录用户userId={}", userId);
            StpUtil.logout();
            return CommonResult.success();
        }catch (Exception e){
            log.error("退出登录异常", e);
            return CommonResult.failed();
        }
    }

}
