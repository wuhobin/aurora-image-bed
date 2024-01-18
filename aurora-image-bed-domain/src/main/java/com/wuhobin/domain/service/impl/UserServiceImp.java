package com.wuhobin.domain.service.impl;

import com.wuhobin.common.api.CommonResult;
import com.wuhobin.domain.dataobject.UserDO;
import com.wuhobin.domain.mapper.UserMapper;
import com.wuhobin.domain.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public CommonResult register(String email, String username, String password) {
        return null;
    }
}
