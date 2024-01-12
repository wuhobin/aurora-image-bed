package com.wuhobin.common.util;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import sun.security.provider.certpath.PKIXTimestampParameters;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author wuhongbin
 * @version 1.0.0
 * @date 2023/5/16 11:03
 * @description redis 生成全局唯一id
 */
@Component
public class RedisIdWorker {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 开始时间
     */
    private static final long BEGIN_TIME = 1640995200L;

    /**
     * 序列号位数
     */
    private static final int BIT_COUNT = 32;


    public long nextId(String keyPrefix) {
        // 1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIME;

        // 2.生成序列号
        // 2.1.获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 2.2.自增长
        long count = redisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);

        // 3.拼接并返回
        return timestamp << BIT_COUNT | count;
    }
}
