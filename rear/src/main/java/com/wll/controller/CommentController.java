package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.pojo.Comment;
import com.wll.service.ICommentService;
import com.wll.utils.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wll
 * @since 2025-02-23
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private ICommentService commentService;


    @GetMapping(params = {"songListId", "userId"})
    public R getCommentBy(int songListId, int userId) {
        return R.success(commentService.list(new LambdaUpdateWrapper<Comment>()
                .eq(Comment::getSongListId, songListId)
                .eq(Comment::getUserId, userId)));
    }


    @PostMapping
    public R saveComment(@RequestBody Comment comment) {
        if (commentService.save(comment)) {
            return R.success(comment.getId());
        }
        return R.error();
    }

    @GetMapping(params = "songListId")
    public R getAllCommentsBy(int songListId) {
        return R.success(commentService.getAllCommentsBy(songListId));
    }


    @PutMapping
    public R updateComment(@RequestBody Comment comment) {
//        return R.success();
        return R.success(commentService.updateById(comment));
    }
}
