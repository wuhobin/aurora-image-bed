package com.wuhobin.nacos;


import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jws.Oneway;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringbootNacosApplication.class})
@Slf4j
public class SpringbootNacosApplicationTests {

    @Test
    public void s() {
        redisTemplate.opsForValue().set("aaa",1);
    }


    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void getConfig(){
        String dataId = "springboot-nacos.yaml";
        String group = "springboot-nacos";
        String config = null;
        ConfigService configService = null;
        try {
            configService = nacosConfigManager.getConfigService();
            config = configService.getConfig(dataId, group, 1000);
            System.out.println(config);
        } catch (NacosException e) {
            e.printStackTrace();
        }

    }

}
