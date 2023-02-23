package com.wuhobin.hellospringbootstarter.config;


import com.wuhobin.hellospringbootstarter.service.MessageService;
import com.wuhobin.hellospringbootstarter.service.impl.MessageServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuhongbin
 * @description: 配置类
 * @datetime 2023/02/23 12:02
 */
@Configuration
@EnableConfigurationProperties({HelloProperties.class})
public class HelloAutoConfiguration {

    @Bean
    public MessageService messageService(HelloProperties helloProperties){
        return new MessageServiceImpl(helloProperties);
    }
}
