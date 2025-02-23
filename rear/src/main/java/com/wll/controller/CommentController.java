package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.pojo.Comment;
import com.wll.service.ICommentService;
import com.wll.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
        return R.success(commentService.save(comment));
    }

    @GetMapping(params = "songListId")
    public R getAllCommentsBy(int songListId) {
        List<Comment> list = commentService.list(new LambdaUpdateWrapper<Comment>()
                .eq(Comment::getSongListId, songListId));
        return R.success(list);
    }
}
