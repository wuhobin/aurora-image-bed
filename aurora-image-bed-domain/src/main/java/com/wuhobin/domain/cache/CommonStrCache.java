package com.wuhobin.domain.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 通用字符串缓存
 * @author wuhongbin
 * @version 1.0.0
 * @date 2024/1/23 16:59
 * @description
 */
@Component
@SuppressWarnings("all")
public class CommonStrCache {

    @Autowired
    private RedisTemplate redisTemplate;

    public void addStrCache(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void addStrCache(String key, String value, Long timeoutHour, TimeUnit timeUnit) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeoutHour, timeUnit);
    }

    public String getStrCache(String key){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return  valueOperations.get(key);
    }
}
