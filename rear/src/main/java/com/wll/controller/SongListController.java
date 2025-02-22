package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.pojo.SongList;
import com.wll.service.ISongListService;
import com.wll.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    private ISongListService songListService;

    @GetMapping
    public R getAllSongList() {
        return R.success(songListService.list());
    }

    @GetMapping(params = "style")
    public R getSongListByStyle(String style) {
        return R.success(songListService.listByMap(Map.of("style", style)));
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


}
