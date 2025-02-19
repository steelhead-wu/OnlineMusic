package com.wll.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wll.mapper.SongListMapper;
import com.wll.pojo.SongList;
import com.wll.service.ISongListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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


}
