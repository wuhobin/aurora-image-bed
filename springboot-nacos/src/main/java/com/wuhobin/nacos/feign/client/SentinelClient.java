package com.wuhobin.nacos.feign.client;

import com.wuhobin.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wuhongbin
 * @version 1.0.0
 * @date 2023/5/17 15:44
 * @description
 */
@FeignClient("springboot-sentinel-service")
public interface SentinelClient {

    @GetMapping("/app/sentinel")
    public CommonResult testSentinel();

}
