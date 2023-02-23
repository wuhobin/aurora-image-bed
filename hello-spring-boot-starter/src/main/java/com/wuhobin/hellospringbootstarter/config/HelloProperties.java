package com.wuhobin.hellospringbootstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuhongbin
 * @description: 配置数据
 * @datetime 2023/02/23 11:56
 */
@Component
@ConfigurationProperties(prefix = "hello")
public class HelloProperties {

    private String name;

    private String message;


    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HelloProperties{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
