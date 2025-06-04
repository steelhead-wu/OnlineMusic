package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.pojo.User;
import com.wll.service.impl.UserServiceImpl;
import com.wll.utils.JWTUtils;
import com.wll.utils.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", maxAge = 3600)
public class UserController {
    @Resource
    private UserServiceImpl userService;


    // 直接获取用户 ID
//    public Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new RuntimeException("用户未登录");
//        }

    // 假设用户信息是 UserDetails 的实现类
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return ((org.springframework.security.core.userdetails.User) userDetails).get(); // 替换为实际获取 ID 的方法
//        return 1L;
//    }


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

        boolean res = userService.updateUserById(user);
        return res ? R.success(true) : R.error();
    }


    @PutMapping("/account")
    public R updateUserByAccount(@RequestBody User user) {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getAccount, user.getAccount());
        boolean res = userService.update(user, lambdaUpdateWrapper);
        return res ? R.success("更新成功", true) : R.error();
    }

    @PostMapping("/registry")
    public R register(@RequestBody User registerUser) {
        String account = userService.register(registerUser);
        return account != null ? R.success("注册成功！快去登录吧~~", account) : R.fatal("内部错误");
    }


    @GetMapping("/account")
    public R getUserByAccount(@RequestBody User user) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        User res = userService.getOne(userLambdaQueryWrapper);
        return Objects.isNull(res) ? R.error() : R.success(res);
    }


    @PostMapping("/login")
    public R login(@RequestBody User loginUser) {
        User user = userService.login(loginUser);
        if (Objects.isNull(user)) return R.error("用户名或密码错误！");
        else {
            return R.success("登录成功", List.of(user,
                    JWTUtils.create(Map.of("user", new User(user.getAccount(), user.getId())), 1000 * 60 * 60 * 24 * 3)));
        }
    }

    @GetMapping(value = "/logoff", params = "id")
    public R logoff(Long id) {
        return R.success(userService.logoff(id));
    }
}
