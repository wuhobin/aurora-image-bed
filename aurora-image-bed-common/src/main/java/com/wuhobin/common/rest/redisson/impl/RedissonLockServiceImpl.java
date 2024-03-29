package com.wuhobin.common.rest.redisson.impl;



import com.wuhobin.common.rest.redisson.RedissonLockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wuhongbin
 * @description: redisson 分布式锁
 * @datetime 2023/02/15 15:25
 */
@Slf4j
@Component
public class RedissonLockServiceImpl implements RedissonLockService {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 默认分布式锁等待时间
     */
    private static final long DEFAULT_TIMEOUT = 5;



    @Override
    public boolean tryLock(String key) {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock();
    }


    @Override
    public boolean lock(long waitTime, String key) throws InterruptedException {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock(waitTime, TimeUnit.SECONDS);
    }

    @Override
    public boolean lock(String key) throws InterruptedException {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }

    @Override
    public boolean lock(long waitTime, long leaseTime, String key) throws InterruptedException {
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
    }

    /**
     * 解锁
     */
    @Override
    public void unLock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}
