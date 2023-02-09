package com.wuhobin.nacos;


import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringbootNacosApplication.class})
@Slf4j
public class SpringbootNacosApplicationTests {

    @Test
    public void s() {

    }


    @Autowired
    private NacosConfigManager nacosConfigManager;

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
