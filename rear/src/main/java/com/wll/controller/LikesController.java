package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.pojo.Likes;
import com.wll.service.ILikesService;
import com.wll.utils.MyUtils;
import com.wll.utils.R;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.util.*;

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

    @PatchMapping
    public R conditionalQuery(@RequestBody Map<String, Likes> map) throws IllegalAccessException {
        Likes fieldLike = map.get("fieldLike");
        Likes conditionalLike = map.get("conditionalLike");
        return R.success(likesService.list(new MyUtils<Likes>().progressQueryField(Likes.class, fieldLike)
                .eq(!Objects.isNull(conditionalLike.getId()), Likes::getId, conditionalLike.getId())
                .eq(!Objects.isNull(conditionalLike.getUserId()), Likes::getUserId, conditionalLike.getUserId())
                .eq(!Objects.isNull(conditionalLike.getSongId()), Likes::getSongId, conditionalLike.getSongId())
        ));
    }
}
