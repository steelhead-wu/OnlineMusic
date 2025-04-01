package com.wll.mapper;

import com.wll.pojo.Likes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wll
 * @since 2025-03-05
 */
public interface LikesMapper extends BaseMapper<Likes> {

    @Insert("""
            insert ignore into likes (user_id, song_id)
            values (#{lk.userId},#{lk.songId})
            """)
    boolean add(@Param("lk") Likes likes);
}
