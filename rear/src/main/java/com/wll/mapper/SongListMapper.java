package com.wll.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wll.pojo.SongList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wll
 * @since 2025-02-18
 */
@Mapper
public interface SongListMapper extends BaseMapper<SongList> {

    @Select("SELECT t.id,t.title,t.pic,t.introduction,t.style,s.title as song,s.id as songId," +
            "s.url AS url,s.album AS album,s.lyric AS lyric,s.picture AS picture FROM song_list t " +
            "LEFT JOIN list_song ls on t.id = ls.song_list_id " +
            "LEFT JOIN song s on ls.song_id = s.id ")
    List<Map<String, Object>> getAllSongList();
}
