package com.wuhobin.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuhobin.common.api.CommonResult;
import com.wuhobin.common.api.ResultCode;
import com.wuhobin.common.config.async.AsyncFactory;
import com.wuhobin.common.config.base.BasePlatformConfig;
import com.wuhobin.common.exception.ServiceException;
import com.wuhobin.common.util.BeanCopyUtil;
import com.wuhobin.common.util.MailUtil;
import com.wuhobin.common.util.security.AESUtil;
import com.wuhobin.domain.cache.CommonStrCache;
import com.wuhobin.domain.constant.CommonConstants;
import com.wuhobin.domain.dataobject.UserDO;
import com.wuhobin.domain.mapper.UserMapper;
import com.wuhobin.domain.service.UserService;
import com.wuhobin.domain.vo.UserVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhongbin
 * @since 2024-01-18
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private CommonStrCache strCache;
    @Autowired
    private BasePlatformConfig basePlatformConfig;
    @Autowired
    private MailUtil mailUtil;

    @Override
    public void save(UserVO user) {
        save(BeanCopyUtil.copy(user, UserDO.class));
    }

    @Override
    public UserVO selectUserByEmail(String email) {
        UserDO one = lambdaQuery().eq(UserDO::getEmail, email).last("limit 0,1").one();
        return BeanCopyUtil.copy(one, UserVO.class);
    }

    @Override
    public UserVO selectUserByUserName(String username) {
        UserDO one = lambdaQuery().eq(UserDO::getUsername, username).last("limit 0,1").one();
        return BeanCopyUtil.copy(one, UserVO.class);
    }

    @Override
    public UserVO selectUser(String email, String username) {
        UserDO one = lambdaQuery().eq(UserDO::getEmail, email).eq(UserDO::getUsername, username).last("limit 0,1").one();
        return BeanCopyUtil.copy(one, UserVO.class);
    }

    @Override
    public CommonResult register(String email, String username, String password) {
        UserVO userVO = null;
        userVO = selectUserByEmail(email);
        if (ObjectUtils.isNotEmpty(userVO)) {
            return CommonResult.failed(ResultCode.EMAIL_EXIST);
        }
        userVO = selectUserByUserName(username);
        if (ObjectUtils.isNotEmpty(userVO)) {
            return CommonResult.failed(ResultCode.USER_NAME_EXIST);
        }
        userVO = buildUser(email, username, password);
        save(userVO);
        String code = RandomStringUtils.randomAlphanumeric(32);
        strCache.addStrCache(String.format(CommonConstants.ACTIVATION_CODE_KEY, email), code, 2L, TimeUnit.HOURS);
        strCache.addStrCache(String.format(CommonConstants.ACTIVATION_CODE_KEY, code), email, 2L, TimeUnit.HOURS);
        AsyncFactory.runAsync(() -> mailUtil.sendMailMessage(Collections.singletonList(email), "您在 遇见 - 图仓 注册的账号已经准备好, 激活后可立即使用.", buildText(username, code)));
        return CommonResult.success();
    }


    private UserVO buildUser(String email, String username, String password){
        UserVO userVO = new UserVO();
        userVO.setEmail(email);
        userVO.setUsername(username);
        try {
            userVO.setPassword(AESUtil.aesEncrypt(password, CommonConstants.AES_KEY));
        } catch (Exception e) {
            throw new ServiceException("aes加密失败");
        }
        userVO.setUserId(RandomStringUtils.randomNumeric(8));
        userVO.setCreateTime(new Date());
        userVO.setUpdateTime(new Date());
        return userVO;
    }

    private String buildText(String username, String code){
        String url = basePlatformConfig.getActivationCodeUrl() + "?code=" + code;
        String text = "您好, 我们收到了您在 遇见 - 图仓 注册 %s 帐户的要求. <br> <br>要完成此过程, 您必须<a href=\"%s\">激活您的账户</a>. <br><br> 另外, 您可以复制并粘贴网址到浏览器:%s<br><br>如果您不打算注册或不是您注册的, 请忽略此消息.";
        return String.format(text, username, url, url);
    }
}
