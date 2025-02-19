package com.wll.controller;

import com.wll.service.ISongListService;
import com.wll.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//        List<Map<String, Object>> maps = songListService.getAllSongList();
        return R.success(songListService.list());
    }

    @GetMapping(params = "style")
    public R getSongListByStyle(String style) {
//        List<Map<String, Object>> maps = songListService.getAllSongList();
        return R.success(songListService.listByMap(Map.of("style", style)));
    }

}
