package com.wll.utils;

import com.wll.pojo.User;
import jakarta.servlet.http.Cookie;

/**
 * @time 2025/3/20 12:07 周四
 */
public class HTTPUtils {
    public static Cookie getCookie(Cookie[] cookies, String cookieName) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) return cookie;
        }
        return null;
    }


    public static Object validateCookie(Cookie cookie) {
//        User user = JWTUtils.verify(cookie.getValue(), "user");
        User user = JWTUtils.verify(cookie.getValue(), "user", User.class);

        return user;
    }


    public static <T> T validateToken(String token, Class<T> clazz) {
//        User user = JWTUtils.verify(cookie.getValue(), "user");
        T user = JWTUtils.verify(token, "user", clazz);

        return user;
    }
}
