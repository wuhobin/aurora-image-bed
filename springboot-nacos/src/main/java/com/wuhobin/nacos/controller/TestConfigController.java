package com.wuhobin.nacos.controller;

import com.wuhobin.common.api.CommonResult;
import com.wuhobin.hellospringbootstarter.service.MessageService;
import com.wuhobin.nacos.feign.client.SentinelClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Value("${app.sss}")
    private String app;

    @GetMapping("/getConfig")
    public CommonResult test(){
        messageService.sayMessage();
        return sentinelClient.testSentinel();
    }




}
