package com.wuhobin.userservice;

import com.wuhobin.common.config.async.AsyncFactory;
import com.wuhobin.common.util.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wuhongbin
 * @version 1.0.0
 * @date 2024/1/16 17:41
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootUserServiceApplication.class)
public class UserTest {

    @Autowired
    private MailUtil mailUtil;

    @Test
    public void test(){
        //mailUtil.sendMailMessage(Collections.singletonList("1289066006@qq.com"), "你好", "您收到的验证码为： 123456");

        AsyncFactory.runAsync(() -> System.out.println("异步执行"));
    }

}
