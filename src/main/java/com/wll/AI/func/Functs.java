package com.wll.AI.func;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wll.pojo.Likes;
import com.wll.pojo.Singer;
import com.wll.pojo.Song;
import com.wll.service.ILikesService;
import com.wll.service.ISongService;
import com.wll.service.impl.SingerServiceImpl;
import com.wll.utils.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.Objects;
import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
public class Functs {

    @Resource
    private ILikesService likesService;


    @Resource
    private ISongService songService;


    @Resource
    private SingerServiceImpl singerService;

    public record MusicUserID(
            @ToolParam(description = "这是歌曲") String song
            , @ToolParam(description = "这是歌手") String singer
            , @ToolParam(description = "当前用户的ID") Long userId
    ) {
    }

    @Bean("addLike")
    @Description("添加音乐到我喜欢,如果失败告诉用户我还无法做到！")
    Function<MusicUserID, Boolean> addLike() {
        return (music) -> {

            Singer singer = singerService.getOne(new LambdaQueryWrapper<Singer>()
                    .select(Singer::getId)
                    .eq(StringUtils.isNotEmpty(music.singer), Singer::getName, music.singer)
            );

            if (Objects.isNull(singer.getId())) {
                return false;
            }

            LambdaQueryWrapper<Song> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper
                    .select(Song::getId)
                    .eq(StringUtils.isNotEmpty(music.song), Song::getTitle, music.song)
                    .eq(Song::getSingerId, singer.getId())
            ;
            Song song = songService.getOne(lambdaQueryWrapper);
            if (Objects.isNull(song) || Objects.isNull(song.getId())) return false;

            return likesService.add(new Likes(music.userId, song.getId()));
        };

    }


}
