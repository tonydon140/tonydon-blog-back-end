package club.tonydon.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 */
public class JwtUtils {

    //有效期为：一个小时，单位为 ms
    public static final Long JWT_TTL = 60 * 60 * 1000L;

    //设置秘钥明文
    public static final String JWT_KEY = "tonydon131020580111400615611shuilanjiao";

    /**
     * 生成 UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成 JWT
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    /**
     * 生成 JWT
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     * 生成 JWT
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    /**
     * 创建 JwtBuilder
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        // 获取安全密钥
        SecretKey secretKey = generalKey();

        // 设置生成时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 设置过期时间
        if (ttlMillis == null) ttlMillis = JWT_TTL;
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid)                // 唯一的ID
                .setSubject(subject)        // 主题  可以是JSON数据
                .setIssuer("tonydon")       // 签发者
                .setIssuedAt(now)           // 签发时间
                .signWith(secretKey)        // 签名
                .setExpiration(expDate);    // 过期时间
    }


    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return 安全的密钥对象
     */
    public static SecretKey generalKey() {
        // 生成安全的密钥对象并返回
        return Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解析 Jwt
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

}