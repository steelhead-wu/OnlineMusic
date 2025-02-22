package com.wll.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wll.mapper.SongListMapper;
import com.wll.pojo.SongList;
import com.wll.service.ISongListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wll
 * @since 2025-02-18
 */
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements ISongListService {
    @Resource
    private SongListMapper songListMapper;


    @Override
    public boolean updateOrSaveRating(long userId, int songListId, int rating) {
        Map<String, Object> scoreBy = songListMapper.getRatingBy(userId, songListId);
        if (Objects.isNull(scoreBy)) {
            return songListMapper.saveRating(userId, songListId, rating);
        } else {
            return songListMapper.updateRatingById((int) scoreBy.get("id"), rating);
        }
    }

    @Override
    public int getEvenRatingOfSongList(int songListId) {
        return songListMapper.getEvenRatingOfSongList(songListId);
    }

    @Override
    public Map<String, Object> getRatingBy(long userId, int songListId) {
        return songListMapper.getRatingBy(userId, songListId);
    }
}
