package com.wll.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wll.pojo.Song;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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


    @Select("SELECT song.id,singer_id,title,album,picture,lyric,url,s.id AS singer_id," +
            "s.name,s.pic,s.sex,s.birth,s.introduction,s.location,s.song_number,s.album_number FROM song " +
            "left join singer s on song.singer_id = s.id WHERE (title LIKE  concat('%', #{kw},'%'))")
    List<Map<String, Object>> searchSongByTitle(String kw);

    List<Song> getLikedSongByUserId(long userId);
}
