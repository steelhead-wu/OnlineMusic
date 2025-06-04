package com.wll.service;

import com.wll.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wll.pojo.DO.CommentDO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wll
 * @since 2025-02-23
 */
public interface ICommentService extends IService<Comment> {
    List<CommentDO> getAllCommentsBy(int songListId);

//    Comment getCommentBy(int songListId, int userId);
}
