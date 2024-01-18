package com.wuhobin.domain.service;

import com.wuhobin.common.api.CommonResult;
import com.wuhobin.domain.dataobject.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhongbin
 * @since 2024-01-18
 */
public interface UserService extends IService<UserDO> {

    CommonResult register(String email, String username, String password);
}
