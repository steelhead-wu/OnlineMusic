package com.wll.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wll.pojo.Song;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wll
 * @since 2025-01-23
 */
public interface SongMapper extends BaseMapper<Song> {

    @Select("select id, singer_id, title, album, picture, lyric, url " +
            "from song where id in " +
            "(select song_id from list_song where song_list_id = #{songListId})")
    List<Song> getSongBySongListID(Integer songListId);
}
