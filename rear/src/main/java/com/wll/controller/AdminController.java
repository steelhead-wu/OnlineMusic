package com.wll.controller;

import com.wll.enums.HTTPStatus;
import com.wll.pojo.Admin;
import com.wll.pojo.Singer;
import com.wll.service.impl.AdminServiceImpl;
import com.wll.service.impl.SingerServiceImpl;
import com.wll.utils.R;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wll
 * @since 2025-04-24
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminServiceImpl adminService;

    @Resource
    private SingerServiceImpl singerService;

    @PostMapping("/login")
    public R login(@RequestBody Admin loginAdmin) {
        Admin admin = adminService.login(loginAdmin);
        if (Objects.isNull(admin)) {
            return R.other(HTTPStatus.CLIENT_ERROR.getCode(), "用户名或者密码错误", null);
        } else {
            return R.success("登录成功", admin);
        }
    }


    @PostMapping("/registry")
    public R registry(@RequestBody Admin registryAdmin) {
        boolean res = adminService.registry(registryAdmin);

        if (res) {
            return R.success("注册成功", res);
        } else {
            return R.other(HTTPStatus.CLIENT_ERROR.getCode(), "不合规范", res);
        }
    }


    @PostMapping("/singer")
    public R conditionalQuerySinger(@RequestBody Singer singer) {
        return R.success(singerService.conditionalQuerySinger(singer));
    }

}
