package com.wll.service;

import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.base.MPJBaseService;
import com.wll.pojo.SongList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wll.utils.R;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wll
 * @since 2025-02-18
 */
public interface ISongListService extends MPJBaseService<SongList>/*IService<SongList>*/ {
    public List<Map<String, Object>> getAllSongList();
}
