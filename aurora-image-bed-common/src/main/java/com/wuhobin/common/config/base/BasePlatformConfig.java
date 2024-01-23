package com.wuhobin.common.config.base;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 *
 * @author wuhongbin
 * @version 1.0.0
 * @date 2024/1/17 12:07
 * @description
 */
@Component
@Data
@RefreshScope
@ConfigurationProperties(prefix = "base.platform.config")
public class BasePlatformConfig {

    /**
     * 七牛文件上传
     */
    public String qiNiuAccessKey;

    /**
     * 七牛密钥
     */
    public String qiNiuSecretKey;

    /**
     * 七牛Bucket
     */
    public String qiNiuBucket;

    public String activationCodeUrl;

}
