package com.wuhobin.nacos.controller;

import cn.hutool.core.lang.hash.Hash;
import com.wuhobin.common.api.CommonResult;
import com.wuhobin.hellospringbootstarter.service.MessageService;
import com.wuhobin.nacos.feign.client.SentinelClient;
import com.wuhobin.springbootdomain.dataobject.UserDO;
import com.wuhobin.springbootdomain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author wuhongbin
 * @description: 测试nacos 配置中心
 * @datetime 2023/02/09 12:05
 */
@Slf4j
@RequestMapping("/app")
@RestController
public class TestConfigController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SentinelClient sentinelClient;

    @Autowired
    private UserService userService;

    @GetMapping("/getConfig")
    public CommonResult test(){
        messageService.sayMessage();
        return sentinelClient.testSentinel();
    }

    @GetMapping("/get")
    public CommonResult getUser(){
        Map<String, Object> map = new HashMap<>();
        UserDO one = userService.lambdaQuery().orderByAsc(UserDO::getId).last("limit 0,1").one();
        map.put("user", one);
        return CommonResult.success(map);
    }


}
