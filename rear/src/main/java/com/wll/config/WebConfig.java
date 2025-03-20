package com.wll.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wll.controller.AiController;
import com.wll.pojo.User;
import com.wll.utils.HTTPUtils;
import com.wll.utils.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @time 2025/2/3 15:32 周一
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private AiController aiController;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", aClass -> aClass.isAnnotationPresent(RestController.class));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowCredentials(true)
//                .allowedOriginPatterns("http://localhost:5173")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .maxAge(-1);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                        Cookie[] cookies = request.getCookies();
                        String path = request.getRequestURI();

                        Cookie us_au;
                        // cookies为空或者不存在名字为us_au的cookie
                        if (Objects.isNull(cookies) || Objects.isNull(us_au = HTTPUtils.getCookie(cookies, "us_au"))
                        ) {
                            System.out.println("拦截器: preHandle - 拦截路径: " + path);
                            return false;
                        }


                        try {
                            HTTPUtils.validateCookie(us_au);
                        } catch (Exception e) {
                            System.out.println("拦截器: preHandle - 拦截路径: " + path);
                            return false;
                        }
                        System.out.println("拦截器: preHandle - 释放路径: " + path);
                        return true;
                    }
                })

                .addPathPatterns("/api/**")
                // 放行路径
                .excludePathPatterns(
                        "/api/user/login"
                        , "/api/user/registry"
                        , "/api/songList"
                        , "/api/songList/random"
                        , "/api/singer"
                        , "/api/singer/random"
                        , "/api/song"
                        , "/api/song/s"
//                        , "/api/ai/**"
                        , "/asset/**"
                )
        ;
    }
}
