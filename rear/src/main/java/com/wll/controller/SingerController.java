package com.wll.controller;

import com.wll.pojo.Singer;
import com.wll.service.ISingerService;
import com.wll.service.impl.SingerServiceImpl;
import com.wll.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wll
 * @since 2025-02-15
 */
@RestController
@RequestMapping("/singer")
public class SingerController {
    @Resource
    private ISingerService singerService;

    @GetMapping
    public R getAllSinger() {
        List<Singer> singers = singerService.list();
        return Objects.isNull(singers) ? R.error() : R.success(singers);
    }

    @GetMapping(params = "sex")
    public R getSingerBySex(Integer sex) {
        List<Singer> res = singerService.listByMap(Map.of("sex", sex));
        return R.success(res);
    }
}
