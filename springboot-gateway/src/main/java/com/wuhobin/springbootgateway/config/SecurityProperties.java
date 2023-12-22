package com.wuhobin.springbootgateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author whb
 */
@ConfigurationProperties(prefix = "gateway.security")
@Component
@RefreshScope
@Data
public class SecurityProperties {
    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {
        "/oauth/**",
        "/actuator/**",
        "/*/v2/api-docs",
        "/swagger/api-docs",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**",
        "/druid/**"
    };

    /**
     * 设置不用认证的url
     */
    private String[] httpUrls = {};


    public String[] getHttpUrls() {
        if (httpUrls == null || httpUrls.length == 0) {
            return ENDPOINTS;
        }
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(ENDPOINTS));
        list.addAll(Arrays.asList(httpUrls));
        return list.toArray(new String[0]);
    }

}
