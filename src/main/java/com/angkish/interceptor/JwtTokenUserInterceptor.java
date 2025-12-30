package com.angkish.interceptor;

import com.angkish.constant.JwtClaimsConstant;
import com.angkish.context.BaseContext;
import com.angkish.properties.JwtProperties;
import com.angkish.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 非 Controller 方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 1. 从请求头获取 token
        String token = request.getHeader(jwtProperties.getUserTokenName());
        if (token == null || token.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            // 2. 校验 JWT
            Claims claims = JwtUtil.parseJWT(
                    jwtProperties.getUserSecretKey(), token);

            Long userId = Long.valueOf(
                    claims.get(JwtClaimsConstant.USER_ID).toString());

            // 3. 从 Redis 中获取 token
            String redisKey = "login:token:" + userId;
            String redisToken = (String) redisTemplate.opsForValue().get(redisKey);

            // 4. Redis 校验（核心）
            if (redisToken == null || !redisToken.equals(token)) {
                log.warn("token已失效，userId={}", userId);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            // 5. 保存当前用户上下文
            BaseContext.setCurrentId(userId);

            return true;
        } catch (Exception e) {
            log.error("token校验失败", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
