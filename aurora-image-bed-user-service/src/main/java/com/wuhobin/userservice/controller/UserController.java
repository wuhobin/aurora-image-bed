package com.wuhobin.userservice.controller;

import com.wuhobin.common.api.CommonResult;
import com.wuhobin.common.api.ResultCode;
import com.wuhobin.common.util.StringUtils;
import com.wuhobin.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关controller
 * @author wuhongbin
 * @version 1.0.0
 * @date 2023/12/22 16:44
 * @description
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param email
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public CommonResult register(String email, String username,String password) {
        if (StringUtils.isAnyBlank(email, username, password)) {
            return CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }
        return userService.register(email, username, password);
    }


    /**
     * 激活账户
     * @param code
     * @return
     */
    @PostMapping("/account/active")
    public CommonResult accountActive(String code) {
        if (StringUtils.isEmpty(code)) {
            return CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }
        return userService.accountActive(code);
    }




}
