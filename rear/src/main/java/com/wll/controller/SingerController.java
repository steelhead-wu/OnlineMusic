package com.wll.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wll.enums.SingerEnum;
import com.wll.pojo.Singer;
import com.wll.service.ISingerService;
import com.wll.utils.R;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;

import java.time.Duration;
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
    @Resource
    private RedisTemplate<String, List<Singer>> redisTemplate;

    @GetMapping
    public R getAllSinger() {
        List<Singer> res = redisTemplate.opsForValue().get(SingerEnum.ALL_SINGER.getValue());
        if (Objects.isNull(res)) {
            res = singerService.list(new LambdaQueryWrapper<Singer>()
                    .eq(Singer::getDeleteFlag, false));
            redisTemplate.opsForValue().set(SingerEnum.ALL_SINGER.getValue(), res, Duration.ofSeconds(1800));
        }

        return R.success(res);
    }

    @GetMapping(params = "sex")
    public R getSingerBySex(Integer sex) {
        List<Singer> res = redisTemplate.opsForValue().get("singer:" + sex);
        if (Objects.isNull(res)) {
            res = singerService.listByMap(Map.of("sex", sex, "delete_flag", false));
            redisTemplate.opsForValue().set("singer:" + sex, res, Duration.ofSeconds(1800));
        }
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
