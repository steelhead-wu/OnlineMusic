package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.pojo.Song;
import com.wll.pojo.User;
import com.wll.service.impl.SongServiceImpl;
import com.wll.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/api/song")
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
    public R addUser(@RequestBody Song song) {
        boolean res;
        try {
            res = songService.save(song);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
        return res ? R.success("创建成功") : R.error("创建失败");
    }

    @PutMapping
    public R updateUserById(@RequestBody Song song) {
        boolean res = songService.updateById(song);
        return res ? R.success() : R.error();
    }


    @GetMapping("/s")
    public R searchSongByTitle(@RequestParam("kw") String kw) {
        LambdaQueryWrapper<Song> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Song::getTitle, kw);
        List<Song> res = songService.list(lambdaQueryWrapper);
        return Objects.isNull(res) ? R.error() : R.success(res);
    }


}
