package com.wll.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wll.pojo.User;
import com.wll.mapper.UserMapper;
import com.wll.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wll
 * @since 2025-01-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;

    public String generateUsername() {
        long uuid = UUID.randomUUID().hashCode();
        return Long.toString(uuid + 9999999999L);
    }

    @Override
    public String register(User registerUser) {
        String nickname = registerUser.getNickname();
        String pwd = registerUser.getPassword();
        if ("".equals(nickname) || "".equals(pwd)) {
            return null;
        }
        // 通过uuid生成用户名
        String account = generateUsername();
        registerUser.setAccount(account);
        int cnt = userMapper.insert(registerUser);
        if (cnt > 0) {
            return account;
        }
        return null;
    }

    @Override
    public User login(User loginUser) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.eq(User::getAccount, loginUser.getAccount()).eq(User::getPassword, loginUser.getPassword());
        return userMapper.selectOne(userLambdaQueryWrapper);
    }
}
