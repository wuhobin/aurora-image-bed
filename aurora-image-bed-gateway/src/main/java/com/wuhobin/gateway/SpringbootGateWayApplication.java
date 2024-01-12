package com.wuhobin.gateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author whb
 */
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.wuhobin")
@MapperScan("com.wuhobin.springbootdomain.mapper")
@SpringBootApplication
public class SpringbootGateWayApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootGateWayApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootGateWayApplication.class, args);
    }

}
