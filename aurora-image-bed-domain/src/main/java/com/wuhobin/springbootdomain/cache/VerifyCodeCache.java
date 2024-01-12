package com.wuhobin.springbootdomain.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author wuhongbin
 * 验证码缓存
 */
@Repository
@Slf4j
public class VerifyCodeCache {

    @Autowired
    private RedisTemplate redisTemplate;


    private static final String ADMIN_MOBILE_VERIFY_CODE = "common-project:admin:user:verify:code:";

    /**
     * 存验证码常量
     */
    public void setVerifyCode(final String mobile, final String code) {
        //1.存入验证码缓存
        String key = ADMIN_MOBILE_VERIFY_CODE;
        redisTemplate.opsForValue().set(key + mobile, code, 60, TimeUnit.SECONDS);
        log.info("成功存入验证码，mobile={},code={}", mobile, code);
    }

    /**
     * 获取缓存的验证码
     *
     * @param mobile
     * @return
     */
    public String getVerifyCode(final String mobile) {
        String key = ADMIN_MOBILE_VERIFY_CODE;
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key + mobile);
    }

    /**
     * 删除缓存的验证码
     *
     * @param mobile
     * @return
     */
    public void deleteVerifyCode(final String mobile) {
        String key = ADMIN_MOBILE_VERIFY_CODE;
        redisTemplate.delete(key + mobile);
    }
}
