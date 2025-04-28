package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wll.pojo.Song;
import com.wll.service.impl.SongServiceImpl;
import com.wll.utils.R;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wll
 * @since 2025-01-23
 */
@RestController
@RequestMapping("/song")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true", maxAge = 3600)
public class SongController {
    @Resource
    private SongServiceImpl songService;


    @GetMapping
    public R getAllSong() {
        List<Song> list = songService.list();
        return Objects.isNull(list) ? R.error() : R.success(list);
    }


    @PostMapping
    public R addSong(@RequestBody Song song) {
        boolean res;
        try {
            res = songService.save(song);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        return res ? R.success("创建成功") : R.error("创建失败");
    }

    @PutMapping
    public R updateSongById(@RequestBody Song song) {
        boolean res = songService.updateById(song);
        return res ? R.success() : R.error();
    }


    @GetMapping("/s")
    public R searchSongByTitle(@RequestParam("kw") String kw) {
        return R.success(songService.searchSongByTitle(kw));
    }


    @GetMapping("/{id}")
    public R getSongById(@PathVariable(name = "id") Integer id) {
        Song song = songService.getById(id);
        return Objects.isNull(song) ? R.error() : R.success(song);
    }


    @PostMapping("/download")
    public ResponseEntity<org.springframework.core.io.Resource> download(@RequestBody Song song) {
        String url = song.getUrl();
        // 从类路径加载文件
        org.springframework.core.io.Resource resource = new ClassPathResource("static" + url);
        // 检查文件是否存在
        if (resource.exists()) {
            // 设置响应头
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // 二进制流类型
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        return ResponseEntity.notFound().build();

    }


    @GetMapping(params = "singerId")
    public R getAllSongBySingerId(Integer singerId) {
        return R.success(songService.list(new LambdaQueryWrapper<Song>().eq(Song::getSingerId, singerId)));
    }


    @GetMapping(params = "songListId")
    public R getSongBySongListID(Integer songListId) {
        return R.success(songService.getSongBySongListID(songListId));
    }


    @GetMapping(params = "userId")
    public R getLikedSongByUserId(long userId) {
        return R.success(songService.getLikedSongByUserId(userId));
    }
}
