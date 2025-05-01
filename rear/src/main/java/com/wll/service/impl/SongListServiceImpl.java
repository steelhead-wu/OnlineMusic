package com.wll.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wll.enums.SongListEnum;
import com.wll.mapper.SongListMapper;
import com.wll.pojo.SongList;
import com.wll.service.ISongListService;
import com.wll.utils.Result;
import com.wll.utils.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.*;

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
    public List<SongList> getRandomSongList(Integer capacity) {
        List<SongList> songLists = songListMapper.selectList(new LambdaQueryWrapper<>());
        Random random = new Random(System.currentTimeMillis());
        Set<Integer> set = new HashSet<>(capacity);
        List<SongList> ans = new ArrayList<>(capacity);
        while (set.size() < capacity) {
            int idx;
            do {
                idx = random.nextInt(songLists.size());
            } while (set.contains(idx));
            set.add(idx);
            ans.add(songLists.get(idx));
        }

        return ans;
    }

    @Override
    public Map<String, Object> getRatingBy(long userId, int songListId) {
        return songListMapper.getRatingBy(userId, songListId);
    }

    @Override
    public SongListEnum chooseCategory(Integer id) {
        return SongListEnum.values()[id];
    }

    public List<SongList> conditionalQuerySongList(SongList songList) {
        return list(new LambdaQueryWrapper<SongList>()
                .eq(SongList::getDeleteFlag, false)
                .eq(Objects.nonNull(songList.getId()), SongList::getId, songList.getId())
                .eq(Objects.nonNull(songList.getRating()), SongList::getRating, songList.getRating())
                .eq(StringUtils.isNotEmpty(songList.getStyle()), SongList::getStyle, songList.getStyle())
        );
    }

    public boolean updateSongListByID(SongList songList) {
        return updateById(songList);
    }

    public boolean deleteSongListByID(Integer id) {
        return updateSongListByID(SongList.builder()
                .id(id)
                .deleteFlag(true)
                .build()
        );
    }
}
