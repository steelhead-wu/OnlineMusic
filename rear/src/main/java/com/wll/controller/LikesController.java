package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.pojo.Likes;
import com.wll.pojo.User;
import com.wll.service.ILikesService;
import com.wll.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

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

    @DeleteMapping
    public R conditionalDelete(@RequestBody Likes likes) {
        return R.success(likesService.remove(new LambdaQueryWrapper<Likes>()
                .eq(!Objects.isNull(likes.getId()), Likes::getId, likes.getId())
                .eq(!Objects.isNull(likes.getUserId()), Likes::getUserId, likes.getUserId())
                .eq(!Objects.isNull(likes.getSongId()), Likes::getSongId, likes.getSongId())
        ));
    }


    @PutMapping
    public R conditionalUpdate(@RequestBody Likes updatedLikes,
                               @RequestParam(required = false) Long id,
                               @RequestParam(required = false) Long userId,
                               @RequestParam(required = false) Long songId) {
        return R.success(likesService.update(new LambdaUpdateWrapper<Likes>()
                .set(!Objects.isNull(updatedLikes.getId()), Likes::getId, updatedLikes.getId())
                .set(!Objects.isNull(updatedLikes.getUserId()), Likes::getUserId, updatedLikes.getUserId())
                .set(!Objects.isNull(updatedLikes.getSongId()), Likes::getSongId, updatedLikes.getSongId())

                .eq(!Objects.isNull(id), Likes::getId, id)
                .eq(!Objects.isNull(userId), Likes::getUserId, userId)
                .eq(!Objects.isNull(songId), Likes::getSongId, songId)
        ));
    }

    @GetMapping()
    public R conditionalQuery(@RequestParam(required = false) Long id,
                              @RequestParam(required = false) Long userId,
                              @RequestParam(required = false) Long songId) {
        return R.success(likesService.list(new LambdaQueryWrapper<Likes>()
                .eq(!Objects.isNull(id), Likes::getId, id)
                .eq(!Objects.isNull(userId), Likes::getUserId, userId)
                .eq(!Objects.isNull(songId), Likes::getSongId, songId)
        ));
    }
}
