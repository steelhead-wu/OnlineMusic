package com.wll.controller;

import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.wll.mapper.SongListMapper;
import com.wll.pojo.Song;
import com.wll.pojo.SongList;
import com.wll.service.ISongListService;
import com.wll.service.impl.SongListServiceImpl;
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
//        MPJLambdaWrapper<SongList> mapMPJLambdaWrapper = new MPJLambdaWrapper<SongList>()
//                .selectAll(SongList.class)
//                .select("s.title as song")
//                .select("s.id as songId")
//                .selectAs("s", Song::getUrl, Song::getUrl)
//                .selectAs("s", Song::getAlbum, Song::getAlbum)
//                .selectAs("s", Song::getLyric, Song::getLyric)
//                .selectAs("s", Song::getPicture, Song::getPicture)
//                .leftJoin("list_song ls on t.id = ls.song_list_id")
//                .leftJoin("song s on ls.song_id = s.id");
//        List<Map<String, Object>> maps = songListService.selectJoinMaps(mapMPJLambdaWrapper);
        List<Map<String, Object>> maps = songListService.getAllSongList();
        return R.success(maps);
    }

}
