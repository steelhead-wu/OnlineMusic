package com.wll.service;

import com.wll.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wll
 * @since 2025-01-20
 */
public interface IUserService extends IService<User> {

    String register(User registerUser);

    User login(User user);
}
