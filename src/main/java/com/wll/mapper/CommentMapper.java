package com.wll.mapper;

import com.wll.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wll.pojo.DO.CommentDO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wll
 * @since 2025-02-23
 */
public interface CommentMapper extends BaseMapper<Comment> {


    List<CommentDO> getAllCommentsBy(int songListId);
}
