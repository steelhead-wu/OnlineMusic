package com.wll.controller;

import com.wll.pojo.Singer;
import com.wll.service.ISingerService;
import com.wll.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

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

    @GetMapping(value = "/random", params = "capacity")
    public R getRandomSinger(Integer capacity) {
        List<Singer> list = singerService.list();
        Random random = new Random(System.currentTimeMillis());
        Set<Integer> set = new HashSet<>(capacity);
        List<Singer> res = new ArrayList<>(capacity);
        int idx;
        while (set.size() < capacity) {
            do {
                idx = random.nextInt(list.size());
            } while (set.contains(idx));
            set.add(idx);
            res.add(list.get(idx));
        }

        return R.success(res);
    }
}
