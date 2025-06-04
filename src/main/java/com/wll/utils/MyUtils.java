package com.wll.utils;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wll.pojo.Likes;
import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.NotNull;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @time 2025/3/6 21:00 周四
 */
public class MyUtils<T> {
    /**
     * @param clazz       所需操作的类对象
     * @param fieldEntity 实体类所需查询字段的类，property为null说明不做查询
     * @return 一个由所需查询字段组成的list
     * @throws IllegalAccessException
     */
    public LambdaQueryWrapper<T> progressQueryField(@NotNull Class<T> clazz, T fieldEntity) throws IllegalAccessException {

        Field[] declaredFields = clazz.getDeclaredFields();
        List<String> fields = new ArrayList<>(declaredFields.length);
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (!Objects.isNull(declaredField.get(fieldEntity))) {
                fields.add(declaredField.getName());
            }
        }
        return new LambdaQueryWrapper<T>().select(clazz, info -> fields.contains(info.getProperty()));
    }



}
