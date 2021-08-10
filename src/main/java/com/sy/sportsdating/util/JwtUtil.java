package com.sy.sportsdating.util;

import cn.hutool.core.collection.CollectionUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 *  @Auther: chengronghua
 * @Date 2021/5/20
 */

@Slf4j
public class JwtUtil {

    /**
     * 密钥
     */
    private static final String SECRET = "asurplus_secret";

    /**
     * 过期时间（单位：秒）
     **/
    private static final long EXPIRATION = 3600L;
    /**
     * 生成用户token,设置token超时时间
     *
     * @param userId
     * @return
     */
    public static String createToken(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                // 添加头部
                .withHeader(map)
                // 放入用户的id
                .withAudience(String.valueOf(userId))
                // 超时设置,设置过期的日期
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                // 签发时间
                .withIssuedAt(new Date())
                // SECRET加密
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 获取用户id
     */
    public static Integer getUserId() {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        // 从请求头部中获取token信息
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (null != jwt) {
                // 拿到我们放置在token中的信息
                List<String> audience = jwt.getAudience();
                if (CollectionUtil.isNotEmpty(audience)) {
                    return Integer.parseInt(audience.get(0));
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 校验token并解析token
     */
    public static ResponseResult verity() {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        // 从请求头部中获取token信息
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            return ResponseResult.error(401, "用户信息已过期，请重新登录");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (null != jwt) {
                // 拿到我们放置在token中的信息
                List<String> audience = jwt.getAudience();
                if (CollectionUtil.isNotEmpty(audience)) {
                    return ResponseResult.success("认证成功", audience.get(0));
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        return ResponseResult.error(401, "用户信息已过期，请重新登录");
    }
}
