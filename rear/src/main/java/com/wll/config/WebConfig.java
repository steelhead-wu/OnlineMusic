package com.wll.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @time 2025/2/3 15:32 周一
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", aClass -> {
            System.out.println(aClass.toGenericString());
            System.out.println(aClass);
            return aClass.isAnnotationPresent(RestController.class);
        });
        WebMvcConfigurer.super.configurePathMatch(configurer);
    }
}
