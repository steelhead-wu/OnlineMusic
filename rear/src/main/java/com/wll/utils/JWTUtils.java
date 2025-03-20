package com.wll.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wll.pojo.User;

import java.util.Date;
import java.util.Map;

/**
 * @time 2025/3/20 7:34 周四
 */
public class JWTUtils {

    /**
     * @param userInfo
     * @param expiration unit millisecond
     * @return
     */
    public static String create(Map<String, Object> userInfo, long expiration) {
        String sign = JWT.create()
//                .withClaim("id", userInfo.get("id"))
//                .withClaim("account", userInfo.get("account"))
                .withHeader(userInfo)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256("123"));// 使用HMAC256算法签名
        return sign;
    }


    public static User verify(String sign, String attribute) throws JWTVerificationException, TokenExpiredException {
        Algorithm algorithm = Algorithm.HMAC256("123");
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT jwt = verifier.verify(sign); // 验证JWT

        Claim user = jwt.getHeaderClaim(attribute);
        return user.as(User.class);
    }
}
