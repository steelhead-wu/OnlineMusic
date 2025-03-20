package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.enums.SongListEnum;
import com.wll.pojo.SongList;
import com.wll.service.ISongListService;
import com.wll.utils.R;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wll
 * @since 2025-02-18
 */
@RestController
@RequestMapping("/songList")
public class SongListController {


    @Resource
    private RedisTemplate<String, List<SongList>> redisTemplate;

    @Resource
    private ISongListService songListService;

    @GetMapping
    public R getAllSongList() {
        List<SongList> res = redisTemplate.opsForValue().get(SongListEnum.ALL_SONG_LIST.getValue());
        if (Objects.isNull(res)) {
            res = songListService.list();
            redisTemplate.opsForValue().set(SongListEnum.ALL_SONG_LIST.getValue(), res, Duration.ofSeconds(1800));
        }
        return R.success(res);
    }

    @GetMapping(params = "id")
    public R getSongListByStyle(Integer id) {
        SongListEnum songListEnum = songListService.chooseCategory(id);
        List<SongList> res = redisTemplate.opsForValue().get(songListEnum.getValue());
        if (Objects.isNull(res)) {
            res = songListService.listByMap(Map.of("style", songListEnum.getOriginalValue()));
            redisTemplate.opsForValue().set(songListEnum.getValue(), res, Duration.ofSeconds(1800));
        }
        return R.success(res);
    }


    @PutMapping(params = {"rating", "songListId", "userId"})
    public R updateOrSaveRating(long userId, int songListId, int rating) {
        return R.success(songListService.updateOrSaveRating(userId, songListId, rating));
    }


    @GetMapping(params = {"songListId", "userId"})
    public R getRatingBy(long userId, int songListId) {
        return R.success(songListService.getRatingBy(userId, songListId));
    }


    @GetMapping(value = "/calculateEvenRating", params = "songListId")
    public R getEvenRatingOfSongList(int songListId) {
        return R.success(songListService.getEvenRatingOfSongList(songListId));
    }


    @PutMapping
    public R updateSongList(@RequestBody SongList songList) {
        return R.success(songListService.update(new LambdaUpdateWrapper<SongList>()
                .set(SongList::getRating, songList.getRating())
                .eq(SongList::getId, songList.getId())));
    }


    @GetMapping("/random")
    public R getRandomSongList(@RequestParam Integer capacity) {
        return R.success(songListService.getRandomSongList(capacity));
    }


}
