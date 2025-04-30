package com.wll.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.enums.ResourcesPath;
import com.wll.pojo.DO.SongDO;
import com.wll.pojo.Song;
import com.wll.mapper.SongMapper;
import com.wll.service.ISongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wll.utils.FilesUtils;
import com.wll.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wll
 * @since 2025-01-23
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements ISongService {

    @Resource
    private SongMapper songMapper;

    public List<SongDO> getSongBySongListID(Integer songListId) {
        return songMapper.getSongBySongListID(songListId);
    }

    public List<Map<String, Object>> searchSongByTitle(String kw) {
        return songMapper.searchSongByTitle(kw);
    }

    @Override
    public List<SongDO> getLikedSongByUserId(long userId) {
        return songMapper.getLikedSongByUserId(userId);
    }

    public List<SongDO> querySongByID(Song song) {
        return songMapper.querySongByID(song);
    }

    public boolean deleteSongByID(Integer id) {
        return update(new LambdaUpdateWrapper<Song>()
                .set(Song::getDeleteFlag, true)
                .eq(Objects.nonNull(id), Song::getId, id)
        );
    }

    public boolean addSong(Song song) {
        song.setId(null);

        // 处理歌词文件
        String lyric_filename = song.getLyricUrl().substring(song.getLyricUrl().lastIndexOf("\\"));
        String src_lyric = "src\\main\\resources\\static%s".formatted(song.getLyricUrl());
        // 读歌词内容
        try {
            String lyric = readFile(src_lyric);
            song.setLyric(StringUtils.isEmpty(lyric) ? "[00:00.00]暂无歌词" : lyric);
        } catch (IOException e) {
            return false;
        }


        boolean res = save(song);

        if (!res) return false;
        String des_lyric = "%s\\%d".formatted(ResourcesPath.LYRIC_PATH.toString(), song.getId());


        // 处理专辑
        String album_filename = song.getPicture().substring(song.getPicture().lastIndexOf("\\"));
        String src_album = "src\\main\\resources\\static%s".formatted(song.getPicture());
        String des_album = "%s\\%d".formatted(ResourcesPath.ALBUM_PICTURE_PATH.toString(), song.getId());


        // 处理歌曲
        String song_filename = song.getUrl().substring(song.getUrl().lastIndexOf("\\"));
        String src_song = "src\\main\\resources\\static%s".formatted(song.getUrl());
        String des_song = "%s\\%d".formatted(ResourcesPath.SONG_PATH.toString(), song.getId());

        try {
            FilesUtils.move(src_album, des_album);
            FilesUtils.move(src_lyric, des_lyric);
            FilesUtils.move(src_song, des_song);
        } catch (IOException e) {
            return false;
        }

        // 更新新的歌词和专辑地址到数据库

        return update(new LambdaUpdateWrapper<Song>()
                .set(Song::getPicture, "%s\\%s".formatted(des_album.substring(des_album.indexOf("\\asset")), album_filename))
                .set(Song::getLyricUrl, "%s\\%s".formatted(des_lyric.substring(des_lyric.indexOf("\\asset")), lyric_filename))
                .set(Song::getUrl, "%s\\%s".formatted(des_song.substring(des_song.indexOf("\\asset")), song_filename))
                .eq(Song::getId, song.getId())
        );

    }

    private String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (Objects.isNull(line)) break;
                sb.append(line);
            }
        }

        return sb.toString();
    }
}
