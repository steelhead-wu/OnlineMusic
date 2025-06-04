package com.wll.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wll.mapper.CommentMapper;
import com.wll.pojo.Comment;
import com.wll.pojo.DO.CommentDO;
import com.wll.service.ICommentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wll
 * @since 2025-02-23
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {


    @Resource
    private CommentMapper commentMapper;
    @Override
    public List<CommentDO> getAllCommentsBy(int songListId) {
        return commentMapper.getAllCommentsBy(songListId);
    }
}
