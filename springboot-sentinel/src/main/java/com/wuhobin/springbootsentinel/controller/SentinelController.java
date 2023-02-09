package com.wuhobin.springbootsentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.wuhobin.common.api.CommonResult;
import com.wuhobin.springbootsentinel.config.sentinel.BlockHandlerUtil;
import com.wuhobin.springbootsentinel.config.sentinel.FallBackUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuhongbin
 * @description: 测试sentinel
 * @datetime 2023/02/09 17:27
 */
@Slf4j
@RestController
@RequestMapping("/app")
public class SentinelController {


    @SentinelResource(value = "testSentinel",blockHandler = "testSentinel",blockHandlerClass = {BlockHandlerUtil.class},fallback = "testSentinel",fallbackClass = {FallBackUtil.class})
    @GetMapping("/sentinel")
    public CommonResult testSentinel(){
        return CommonResult.success("sssssssssss");
    }

}
