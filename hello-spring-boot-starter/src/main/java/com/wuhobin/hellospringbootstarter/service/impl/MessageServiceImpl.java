package com.wuhobin.hellospringbootstarter.service.impl;


import com.wuhobin.hellospringbootstarter.config.HelloProperties;
import com.wuhobin.hellospringbootstarter.service.MessageService;

/**
 * @author wuhongbin
 * @description: 实现类
 * @datetime 2023/02/23 12:03
 */
public class MessageServiceImpl implements MessageService {

    private final HelloProperties helloProperties;


    public MessageServiceImpl(HelloProperties helloProperties){
        this.helloProperties = helloProperties;
    }


    @Override
    public void sayMessage() {
        System.out.println("你好：" + helloProperties.getMessage());
    }
}
