package com.wll.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wll.enums.ResourcesPath;
import com.wll.pojo.Singer;
import com.wll.mapper.SingerMapper;
import com.wll.service.ISingerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wll.utils.FilesUtils;
import com.wll.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wll
 * @since 2025-02-15
 */
@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements ISingerService {
    public List<Singer> querySingerByID(Singer singer) {
        return list(new LambdaQueryWrapper<Singer>()
                        .select(
                                List.of(Singer::getId, Singer::getName, Singer::getSex, Singer::getBirth,
                                        Singer::getIntroduction, Singer::getLocation, Singer::getPic
                                ))
                        .eq(Singer::getDeleteFlag, false)
                        .eq(Objects.nonNull(singer.getId()), Singer::getId, singer.getId())
        );
    }

    public boolean deleteSingerById(Integer id) {
        return updateSingerByID(Singer.builder()
                .id(id)
                .deleteFlag(true)
                .build());
    }

    public boolean updateSingerByID(Singer singer) {
        return update(new LambdaUpdateWrapper<Singer>()
                .set(Objects.nonNull(singer.getIntroduction()), Singer::getIntroduction, singer.getIntroduction())
                .set(Objects.nonNull(singer.getBirth()), Singer::getBirth, singer.getBirth())
                .set(Objects.nonNull(singer.getPic()), Singer::getPic, singer.getPic())
                .set(Objects.nonNull(singer.getLocation()), Singer::getLocation, singer.getLocation())
                .set(Objects.nonNull(singer.getAlbumNumber()), Singer::getAlbumNumber, singer.getAlbumNumber())
                .set(Objects.nonNull(singer.getSongNumber()), Singer::getSongNumber, singer.getSongNumber())
                .set(Objects.nonNull(singer.getDeleteFlag()), Singer::getDeleteFlag, singer.getDeleteFlag())
                .set(Objects.nonNull(singer.getName()), Singer::getName, singer.getName())
                .set(Objects.nonNull(singer.getSex()), Singer::getSex, singer.getSex())
                .eq(Objects.nonNull(singer.getId()), Singer::getId, singer.getId())
        );
    }

    /**
     * add singer
     */
    public boolean addSinger(Singer singer) {
        singer.setId(null);
        boolean res = save(singer);
        if (res) {
            // move the position of singer avatar
            int src_filename_idx = singer.getPic().lastIndexOf('\\');
            if (src_filename_idx < 2) throw new RuntimeException("文件路径不合法");

            String source = "src/main/resources/static".concat(singer.getPic());
            String src_filename = singer.getPic().substring(src_filename_idx);


            String s = "%s\\%d".formatted(singer.getPic().substring(0, src_filename_idx - 2), singer.getId());
            String target = "src/main/resources/static%s".formatted(s);


            try {
                FilesUtils.move(source, target);
            } catch (IOException e) {
                throw new RuntimeException("文件已经存在");
            }

            singer.setPic(s.concat(src_filename));
            return updateSingerByID(singer);
        }
        return false;
    }

    public List<Singer> searchSinger(String keyword) {
        return list(new LambdaQueryWrapper<Singer>()
                .eq(Singer::getDeleteFlag, false)
                .like(StringUtils.isNotEmpty(keyword), Singer::getName, keyword)
                .or()
                .like(StringUtils.isNotEmpty(keyword), Singer::getLocation, keyword)
                .or()
                .like(StringUtils.isNotEmpty(keyword), Singer::getBirth, keyword)
                .or()
                .like(StringUtils.isNotEmpty(keyword), Singer::getIntroduction, keyword)
                .or()
                .like(StringUtils.isNotEmpty(keyword), Singer::getSex, keyword)
        );
    }
}
