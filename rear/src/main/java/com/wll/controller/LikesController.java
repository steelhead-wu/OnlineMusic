package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wll.pojo.Likes;
import com.wll.pojo.User;
import com.wll.service.ILikesService;
import com.wll.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wll
 * @since 2025-03-05
 */
@RestController
@RequestMapping("/likes")
public class LikesController {
    @Resource
    private ILikesService likesService;

    @PostMapping
    public R addLike(@RequestBody Likes likes) {
        return R.success(likesService.save(likes));
    }


    @GetMapping(params = {"userId", "songId"})
    public R getLikeBy(long userId, int songId) {
        return R.success(likesService.getOne(new LambdaQueryWrapper<Likes>()
                .eq(Likes::getUserId, userId)
                .eq(Likes::getSongId, songId)));
    }

    @DeleteMapping
    public R delLikeBy(@RequestBody Likes likes) {
        return R.success(likesService.remove(new LambdaQueryWrapper<Likes>()
                .eq(Likes::getUserId, likes.getUserId())
                .eq(Likes::getSongId, likes.getSongId())));
    }
}
