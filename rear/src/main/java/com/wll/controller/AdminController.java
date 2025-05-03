package com.wll.controller;

import com.wll.enums.HTTPStatus;
import com.wll.enums.ResourcesPath;
import com.wll.pojo.Admin;
import com.wll.pojo.DO.SongDO;
import com.wll.pojo.Singer;
import com.wll.pojo.Song;
import com.wll.pojo.SongList;
import com.wll.service.impl.AdminServiceImpl;
import com.wll.service.impl.SingerServiceImpl;
import com.wll.service.impl.SongListServiceImpl;
import com.wll.service.impl.SongServiceImpl;
import com.wll.utils.FilesUtils;
import com.wll.utils.R;
import com.wll.utils.Result;
import com.wll.utils.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wll
 * @since 2025-04-24
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminServiceImpl adminService;

    @Resource
    private SingerServiceImpl singerService;

    @Resource
    private SongServiceImpl songService;

    @Resource
    private SongListServiceImpl songListService;

    @Resource
    private FileController fileController;

    @PostMapping("/login")
    public Result login(@RequestBody Admin loginAdmin) {
        Admin admin = adminService.login(loginAdmin);
        if (Objects.isNull(admin)) {
            return Result.other(HTTPStatus.CLIENT_ERROR.getCode(), "用户名或者密码错误", null);
        } else {
            return Result.success("登录成功", admin);
        }
    }


    @PostMapping("/registry")
    public Result registry(@RequestBody Admin registryAdmin) {
        boolean res = adminService.registry(registryAdmin);

        if (res) {
            return Result.success("注册成功", res);
        } else {
            return Result.other(HTTPStatus.CLIENT_ERROR.getCode(), "不合规范", res);
        }
    }

    /**
     * 根据ID查询歌手
     */
    @PostMapping("/singer")
    public Result querySingerByID(@RequestBody Singer singer) {
        return Result.success(singerService.querySingerByID(singer));
    }


    /**
     * 根据id删除歌手
     */
    @PutMapping(value = "/singer/delete", params = "id")
    public Result deleteSingerById(Integer id) {
        return Result.success(singerService.deleteSingerById(id));
    }

    /**
     * 更新歌手
     */
    @PutMapping(value = "/singer")
    public Result updateSinger(@RequestBody Singer singer) {
        return Result.success(singerService.updateSingerByID(singer));
    }

    @PostMapping(value = "/singer/add")
    public Result addSinger(@RequestBody Singer singer) {
        return Result.success(singerService.addSinger(singer));
    }

    @GetMapping(value = "/singer/s", params = "keyword")
    public Result searchSinger(String keyword) {
        return Result.success(singerService.searchSinger(keyword));
    }


    @PatchMapping("/song")
    public Result querySongByID(@RequestBody Song song) {
        return Result.success(songService.querySongByID(song));
    }


    @PutMapping(value = "/song", params = "isUploadLyric")
    public Result updateSongByID(@RequestBody Song song, Boolean isUploadLyric) {
        if (isUploadLyric) {
            try {
                song.setLyric(FilesUtils.readFile("%s\\%d\\%s".formatted(
                        ResourcesPath.LYRIC_PATH.toString()
                        , song.getId()
                        , song.getLyricUrl().substring(song.getLyricUrl().lastIndexOf('\\')))));
            } catch (IOException e) {
                return Result.serverError("读取文件失败", false);
            }
        }
        return Result.success(songService.updateById(song));
    }

    @PutMapping(value = "/song/delete", params = "id")
    public Result deleteSongByID(Integer id) {
        return Result.success(songService.deleteSongByID(id));
    }


    @PostMapping("/song")
    public Result addSong(@RequestBody Song song) {
        return Result.success(songService.addSong(song));
    }

    @GetMapping(value = "/song/s", params = "keyword")
    public Result searchSong(String keyword) {
        return Result.success(songService.searchSong(keyword));
    }

    @PatchMapping("/song-list")
    public Result conditionalQuerySongList(@RequestBody SongList songList) {
        return Result.success(songListService.conditionalQuerySongList(songList));
    }

    @PutMapping("/song-list")
    public Result updateSongListByID(@RequestBody SongList songList) {
        return Result.success(songListService.updateSongListByID(songList));
    }

    @PutMapping(value = "/song-list", params = "id")
    public Result deleteSongListByID(Integer id) {
        return Result.success(songListService.deleteSongListByID(id));
    }

    @PostMapping("/song-list/uploadImg")
    public Result uploadSongListImg(@RequestParam("blob") MultipartFile multipartFile,
                                    @RequestParam("Picture-Repo-Type") int pictureRepoType,
                                    @RequestParam("ID") String ID,
                                    HttpServletRequest request) {
        R file_upload_res = fileController.upload(multipartFile, pictureRepoType, ID, request);
        if (file_upload_res.getCode() == HTTPStatus.OK.getCode()) {
            String file_path = file_upload_res.getLink().substring(file_upload_res.getLink().indexOf("\\asset"));
            Result result = updateSongListByID(SongList.builder()
                    .id(Integer.valueOf(ID))
                    .pic(file_path)
                    .build());
            if ((Boolean) result.getData()) {
                return Result.success(file_path);
            }
            return Result.serverError("文件路径更新失败！", false);
        }
        return Result.serverError("服务器内部错误！", false);
    }


    @PostMapping("/song-list")
    public Result addSongList(@RequestPart("blob") MultipartFile multipartFile,
                              @RequestParam("Picture-Repo-Type") int pictureRepoType,
                              @RequestPart("songList") SongList songList,
                              HttpServletRequest request) {
        // 对象用RequestPart传输

        if (StringUtils.isEmpty(songList.getTitle())) {
            return Result.clientError("标题不可为空", false);
        }
        boolean save_res = songListService.save(songList);
        if (save_res) {
            R upload_res = fileController.upload(multipartFile, pictureRepoType, songList.getId().toString(), request);
            if (upload_res.getCode() == HTTPStatus.OK.getCode()) {
                songList.setPic(upload_res.getLink().substring(upload_res.getLink().indexOf("\\asset")));
                songListService.updateById(songList);
                return Result.success("新增成功", true);
            } else {
                return Result.serverError("照片上传失败", false);
            }
        } else
            return Result.serverError("新增失败！",false);
    }

    @GetMapping(value = "/song-list/s", params = "keyword")
    public Result searchSongList(String keyword) {
        return Result.success(songListService.searchSongList(keyword));
    }


    @GetMapping(value = "/song-list/detail",params = "songListID")
    public Result getSongBySongListID(Integer songListID) {
        return Result.success(songService.getSongBySongListID(songListID));
    }




}
