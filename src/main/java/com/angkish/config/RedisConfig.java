package com.angkish.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public ObjectMapper redisObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // 支持 Java 8 时间 API（否则 LocalDateTime 会序列化失败）
        mapper.registerModule(new JavaTimeModule());

        // 如有需要，可在这里统一配置日期格式、序列化策略等
        // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }

    @Bean
    public RedisSerializer<Object> redisValueSerializer(
            ObjectMapper redisObjectMapper) {

        return new GenericJackson2JsonRedisSerializer(redisObjectMapper);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory connectionFactory,
            RedisSerializer<Object> redisValueSerializer) {

        // 创建 RedisTemplate 实例
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置连接工厂（Spring Boot 自动配置）
        template.setConnectionFactory(connectionFactory);

        // ---------------- Key 序列化 ----------------

        // Key / HashKey 使用字符串序列化
        // 优点：Redis 可直接查看 key，不乱码
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        // ---------------- Value 序列化 ----------------

        // Value / HashValue 使用 JSON 序列化
        // 保证复杂对象可读、可跨语言
        template.setValueSerializer(redisValueSerializer);
        template.setHashValueSerializer(redisValueSerializer);

        // 初始化 RedisTemplate（必须调用）
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisCacheManager redisCacheManager(
            RedisConnectionFactory connectionFactory,
            RedisSerializer<Object> redisValueSerializer) {

        // 默认缓存配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()

                // 设置默认过期时间
                // 防止缓存永久存在，作为数据一致性的兜底方案
                .entryTtl(Duration.ofHours(6))

                // Key 序列化方式
                // Spring Cache 的 key 本质也是 Redis key，必须与 RedisTemplate 保持一致
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new StringRedisSerializer()))

                // Value 序列化方式
                // 使用与 RedisTemplate 完全相同的序列化器
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(redisValueSerializer))

                // 禁止缓存 null 值（强烈推荐）
                // 作用：
                // - 防止缓存穿透
                // - 避免“查不到数据却被缓存住”的问题
                .disableCachingNullValues();

        // 构建 RedisCacheManager
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }

}
