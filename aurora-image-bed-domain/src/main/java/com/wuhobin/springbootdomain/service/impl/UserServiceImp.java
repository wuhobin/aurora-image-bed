package com.wuhobin.springbootdomain.service.impl;

import com.wuhobin.springbootdomain.dataobject.UserDO;
import com.wuhobin.springbootdomain.mapper.UserMapper;
import com.wuhobin.springbootdomain.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuhongbin
 * @since 2023-05-08
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, UserDO> implements UserService {

}
