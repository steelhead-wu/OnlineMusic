package com.wll;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @time 2025/1/20 17:24 周一
 */
@SpringBootApplication
@MapperScan("com.wll.mapper")
public class OnlineMusicApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineMusicApplication.class, args);
    }
}
