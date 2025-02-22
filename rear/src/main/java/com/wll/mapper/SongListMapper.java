package com.wll.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wll.pojo.SongList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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


    @Update("update user_songlist_rating" +
            "       set rating = #{rating}" +
            "        where user_id = #{userId}" +
            "          and song_list_id = #{songListId};")
    boolean updateRating(int userId, int songListId, int rating);

    @Update("update user_songlist_rating set rating = #{rating}  where id = #{id}")
    boolean updateRatingById(int id, int rating);

    @Insert("insert into user_songlist_rating (user_id, song_list_id, rating) values (#{userId},#{songListId},#{rating})")
    boolean saveRating(long userId, int songListId, int rating);


    @Select("select id,rating from user_songlist_rating where song_list_id = #{songListId} and user_id = #{userId}")
    Map<String, Object> getRatingBy(long userId, int songListId);

    @Select("select avg(rating) from user_songlist_rating where song_list_id = #{songListId}")
    int getEvenRatingOfSongList(int songListId);
}
