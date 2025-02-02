package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.pojo.User;
import com.wll.service.impl.UserServiceImpl;
import com.wll.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wll
 * @since 2025-01-20
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true", maxAge = 3600)
public class UserController {
    @Resource
    private UserServiceImpl userService;


    @GetMapping
    public R getAllUser() {
        List<User> list = userService.list();
        return Objects.isNull(list) ? R.error() : R.success(list);
    }


    @PostMapping
    public R addUser(@RequestBody User user) {
        boolean res;
        try {
            res = userService.save(user);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        return res ? R.success("创建成功") : R.error("创建失败");
    }

    @PutMapping
    public R updateUserById(@RequestBody User user) {
        boolean res = userService.updateById(user);
        return res ? R.success() : R.error();
    }

    /**
     * @param user
     * @return
     */
    @PutMapping("/account")
    public R updateUserByAccount(@RequestBody User user) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getAccount, user.getAccount());
        boolean res = userService.update(user, lambdaUpdateWrapper);
        return res ? R.success("更新成功", res) : R.error();
    }

    @PostMapping("/registry")
    public R register(@RequestBody User registerUser) {
        String account = userService.register(registerUser);
        return account != null ? R.success("注册成功！快去登录吧~~", account) : R.fatal("内部错误");
    }


    @GetMapping("/account")
    public R getUserByAccount(@RequestBody User user) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        User res = userService.getOne(userLambdaQueryWrapper);
        return Objects.isNull(res) ? R.error() : R.success(res);
    }


    @PostMapping("/login")
    public R login(@RequestBody User loginUser) {
        User user = userService.login(loginUser);
        return Objects.isNull(user) ? R.error("用户名或密码错误！") : R.success("登录成功",user);
    }



}
