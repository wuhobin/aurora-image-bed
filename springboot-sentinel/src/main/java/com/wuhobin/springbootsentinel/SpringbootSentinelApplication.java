package com.wuhobin.springbootsentinel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@ComponentScan(basePackages = "com.wuhobin")
@MapperScan("com.wuhobin.springbootdomain.mapper")
@SpringBootApplication
public class SpringbootSentinelApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootSentinelApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSentinelApplication.class, args);
    }

}
