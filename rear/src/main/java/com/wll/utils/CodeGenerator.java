package com.wll.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

/**
 * @time 2025/1/20 14:03 周一
 */
public class CodeGenerator {
    public static void main(String[] args) {
        // 使用 FastAutoGenerator 快速配置代码生成器
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/online_music", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("wll") // 设置作者
                            .enableSwagger()
                            .outputDir("D:/new"); // 输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.wll") // 设置父包名
                            .entity("pojo") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .xml("resources.mapper"); // 设置 Mapper XML 文件包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("song") // 设置需要生成的表名
                            .entityBuilder()
                            .enableLombok() // 启用 Lombok
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder()
                            .enableRestStyle(); // 启用 REST 风格
                })
                .templateEngine(new VelocityTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }
}
