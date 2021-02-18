package com.dongdl.springboot1.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.dongdl.springboot1.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/8/5 11:03 UTC+8
 * @description
 */
@Slf4j
public class JwtUtil {

    private static final String SECRET = "xxxxxxxxxxxxxxxxxxxxxxxx";
    private static final Integer SECONDS = 60 * 60 * 8;
    private static final String ISSUER = "zzt002";
    private static final String AUTHENTICATION = "Authentication";

    public static String createToken(Integer seconds, Map<String, Object> map) {
        JWTCreator.Builder builder = JWT.create();
        builder.withIssuer(ISSUER)
                .withNotBefore(DateUtil.getCurrentDate())
                .withExpiresAt(DateUtil.getCurrentDate(seconds == null ? SECONDS : seconds));
        if (map != null && !map.isEmpty()) {
            builder.withClaim(AUTHENTICATION, map);
        }
        return builder.sign(getAlgorithm());
    }

    public static String createToken() {
        return createToken(null, null);
    }

    public static String createToken(Integer seconds) {
        return createToken(seconds, null);
    }

    public static String createToken(Map<String, Object> map) {
        return createToken(null, map);
    }

    /**
     * 签名合法性验证
     *
     * @param token
     * @return
     */
    public static boolean verifyToken(String token) {
        JWTVerifier jv = JWT.require(getAlgorithm()).build();
        try {
            jv.verify(token);
        } catch (JWTVerificationException e) {
            log.info("token异常:{}", e.getMessage());
            return false;
        }
        return true;
    }

    public static List<String> getAudience(String token) {
        return JWT.decode(token).getAudience();
    }

    public static Date getExpiresAt(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    public static Map<String, Claim> getClaim(String token) {
        return JWT.decode(token).getClaims();
    }

    private static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET);
    }
}
