package com.wll.service.impl;

import com.wll.pojo.Song;
import com.wll.mapper.SongMapper;
import com.wll.service.ISongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public List<Song> getSongBySongListID(Integer songListId) {
        return songMapper.getSongBySongListID(songListId);
    }
}
