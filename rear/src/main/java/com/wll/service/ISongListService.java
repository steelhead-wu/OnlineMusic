package com.wll.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wll.pojo.SongList;

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
public interface ISongListService extends IService<SongList> {
    public List<Map<String, Object>> getAllSongList();
}
