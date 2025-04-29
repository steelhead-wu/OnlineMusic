package com.wll.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.pojo.DO.SongDO;
import com.wll.pojo.Song;
import com.wll.mapper.SongMapper;
import com.wll.service.ISongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

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
}
