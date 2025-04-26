package com.wll.config;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wll.pojo.User;
import com.wll.utils.HTTPUtils;
import com.wll.utils.R;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.model.Media;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @time 2025/2/3 15:32 周一
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", aClass -> aClass.isAnnotationPresent(RestController.class));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowCredentials(true)
//                .allowedOriginPatterns("http://localhost:5173")
                .allowedOrigins("http://localhost:5173", "http://localhost:5174")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
                        Cookie[] cookies = request.getCookies();
                        String path = request.getRequestURI();
                        System.out.println("Request Method: " + request.getMethod());
                        System.out.println("Request Headers: " + Collections.list(request.getHeaderNames()));
                        Cookie us_au;
                        // cookies为空或者不存在名字为us_au的cookie
                        if (Objects.isNull(cookies) || Objects.isNull(us_au = HTTPUtils.getCookie(cookies, "us_au"))
                        ) {
                            System.out.println("拦截器: preHandle - 拦截路径: " + path);

                            // 未登录无法评论
                            if ("/api/comment".equals(path)) {
                                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                response.setCharacterEncoding("utf-8");
                                new ObjectMapper().writeValue(response.getWriter(), R.unAuthorization());
                            }


                            return false;
                        }

                        User user;
                        try {
                            user = (User) HTTPUtils.validateCookie(us_au);
                        } catch (Exception e) {
                            System.out.println("拦截器: preHandle - 拦截路径: " + path);
                            return false;
                        }
//                        userContext.setCurrentUser(user);
//                        userContext.setCurrentUser(user.getId());

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
                        , "/api/admin/**"
                        , "/asset/**"
                )
        ;
    }


    /*返回客户端数据*/
    private void returnJson(HttpServletResponse response, int code, String message) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(code);
        try {

            response.getWriter()
                    .print(Map.of("status", 401))
            ;


        } catch (IOException e) {
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
