package com.wll.AI.func;

import com.wll.controller.LikesController;
import com.wll.pojo.Likes;
import com.wll.service.ILikesService;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.Locale;
import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
public class Functs {

    @Resource
    private ILikesService likesService;

    @Resource
    private LikesController likesController;

    public record MusicUserID(@ToolParam(description = "歌曲") String name, Long kj,
                              @ToolParam(description = "歌手") String k) {
    }

    @Bean
    @Description("添加音乐到我喜欢")
    Function<MusicUserID, Boolean> addLike() {
        return (music) -> {
            System.out.println("music.name" + music.name());
            System.out.println("music.userId" + music.kj());
            System.out.println("music.zjl" + music.k());
            return true;
        };
//        return (likes) -> (boolean) likesController.addLike(likes).getData();
    }
}
