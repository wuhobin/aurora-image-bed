package com.wuhobin.uaa;

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
public class SpringbootUaaApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootUaaApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootUaaApplication.class, args);
    }

}
