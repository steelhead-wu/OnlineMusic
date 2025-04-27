package com.wll.controller;

import com.wll.enums.HTTPStatus;
import com.wll.pojo.Admin;
import com.wll.pojo.Singer;
import com.wll.service.impl.AdminServiceImpl;
import com.wll.service.impl.SingerServiceImpl;
import com.wll.utils.R;
import com.wll.utils.Result;
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
    public Result login(@RequestBody Admin loginAdmin) {
        Admin admin = adminService.login(loginAdmin);
        if (Objects.isNull(admin)) {
            return Result.other(HTTPStatus.CLIENT_ERROR.getCode(), "用户名或者密码错误", null);
        } else {
            return Result.success("登录成功", admin);
        }
    }


    @PostMapping("/registry")
    public Result registry(@RequestBody Admin registryAdmin) {
        boolean res = adminService.registry(registryAdmin);

        if (res) {
            return Result.success("注册成功", res);
        } else {
            return Result.other(HTTPStatus.CLIENT_ERROR.getCode(), "不合规范", res);
        }
    }

    /**
     * 根据条件查询歌手
     */
    @PostMapping("/singer")
    public Result conditionalQuerySinger(@RequestBody Singer singer) {
        return Result.success(singerService.conditionalQuerySinger(singer));
    }


    /**
     * 根据id删除歌手
     */
    @PutMapping(value = "/singer/delete", params = "id")
    public Result deleteSingerById(Integer id) {
        return Result.success(singerService.deleteSingerById(id));
    }

    /**
     * 更新歌手
     */
    @PutMapping(value = "/singer")
    public Result updateSinger(@RequestBody Singer singer) {
        return Result.success(singerService.updateSingerByID(singer));
    }

    @PostMapping(value = "/singer/add")
    public Result addSinger(@RequestBody Singer singer) {
        return Result.success(singerService.addSinger(singer));
    }

}
