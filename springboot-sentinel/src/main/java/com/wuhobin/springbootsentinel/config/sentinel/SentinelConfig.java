package com.wuhobin.springbootsentinel.config.sentinel;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author wuhongbin
 * @description: 配置中心读取sentinel配置
 * @datetime 2023/02/09 16:27
 */
@Slf4j
@Configuration
public class SentinelConfig {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Value("${spring.cloud.nacos.config.extension-configs[2].data-id}")
    private String dataId;
    @Value("${spring.cloud.nacos.config.extension-configs[2].group}")
    private String groupId;


    /**
     * 开启sentinel注解支持
     * @return
     */
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }


    @PostConstruct
    public void initSentinelRules() {
        log.info("初始化sentinel rules dataId ---> {},groupId ---> {}", dataId, groupId);
        ConfigService configService = null;
        String config = null;
        try {
            configService = nacosConfigManager.getConfigService();
            config = configService.getConfig(dataId, groupId, 3000);
            configService.addListener(dataId, groupId, new AbstractListener() {

                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("初始化sentinel rules configInfo={}", configInfo);
                    SentinelRule sentinelRule = JSON.parseObject(configInfo, new TypeReference<SentinelRule>() {
                    });
                    FlowRuleManager.loadRules(sentinelRule.getFlowRules());
                    DegradeRuleManager.loadRules(sentinelRule.getDegradeRules());
                }
            });


        } catch (NacosException e) {
            log.info("初始化sentinel rules 出错");
            e.printStackTrace();
        }

        if (StringUtils.isNotEmpty(config)) {
            SentinelRule sentinelRule = JSON.parseObject(config, new TypeReference<SentinelRule>() {
            });
            log.info("get flow rules is {}", sentinelRule);
            FlowRuleManager.loadRules(sentinelRule.getFlowRules());
            DegradeRuleManager.loadRules(sentinelRule.getDegradeRules());
        }


    }


}
