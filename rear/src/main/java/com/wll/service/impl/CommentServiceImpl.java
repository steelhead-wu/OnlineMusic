package com.wll.service.impl;

import com.wll.pojo.Comment;
import com.wll.mapper.CommentMapper;
import com.wll.pojo.DO.CommentDO;
import com.wll.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
