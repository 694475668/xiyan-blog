package com.xiyan.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiyan.constants.Constant;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 *
 * @author 刘明
 */
public class JWTUtil {
    /**
     * 生成Token
     *
     * @param userId
     * @param username
     * @return
     * @throws Exception
     */
    public static String createToken(String userId, String username) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, Constant.JWT_TTL);
        Date expireDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)//头
                .withClaim("userId", userId)
                .withClaim("username", username)
                .withSubject("令牌")//
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(Constant.SECRET));//签名
        return token;
    }

    /**
     * 验证Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Constant.SECRET)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }
}