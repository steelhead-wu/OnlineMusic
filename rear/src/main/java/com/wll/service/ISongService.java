package com.wll.service;

import com.wll.pojo.Song;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wll
 * @since 2025-01-23
 */
public interface ISongService extends IService<Song> {

    List<Song> getLikedSongByUserId(long userId);
}
