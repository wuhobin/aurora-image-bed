package com.wuhobin.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wuhobin.common.api.CommonResult;
import com.wuhobin.domain.dataobject.UserDO;
import com.wuhobin.domain.vo.UserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuhongbin
 * @since 2024-01-18
 */
public interface UserService extends IService<UserDO> {

    void save(UserVO user);

    UserVO selectUserByEmail(String email);

    UserVO selectUserByUserName(String username);

    UserVO selectUser(String email, String username);



    CommonResult register(String email, String username, String password);
}
