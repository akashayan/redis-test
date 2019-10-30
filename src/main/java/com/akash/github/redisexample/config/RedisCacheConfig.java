package com.akash.github.redisexample.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisCacheConfig {

    @Resource(name = "configurationProperties")
    private ConfigurationProperties configurationProperties;


    @Bean("redisConnectionFactory")
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory
                = new LettuceConnectionFactory(redisStandaloneConfiguration(), lettuceClientConfiguration());
        lettuceConnectionFactory.setShareNativeConnection(false);
        return lettuceConnectionFactory;
    }


    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    private RedisStandaloneConfiguration redisStandaloneConfiguration() {
        return new RedisStandaloneConfiguration(configurationProperties.getRedis().getHostName(), configurationProperties.getRedis().getPort());
    }

    private LettuceClientConfiguration lettuceClientConfiguration() {
        LettuceClientConfiguration lettuceClientConfiguration
                = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(configurationProperties.getRedisPool().getTimeout()))
                .poolConfig(poolConfig())
                .build();
        return lettuceClientConfiguration;
    }

    private GenericObjectPoolConfig poolConfig() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setTestOnReturn(true);
        poolConfig.setMaxIdle(configurationProperties.getRedisPool().getMaxIdle());
        poolConfig.setMinIdle(configurationProperties.getRedisPool().getMinIdle());
        poolConfig.setMaxWaitMillis(configurationProperties.getRedisPool().getMaxWaitMillis());
        poolConfig.setMaxTotal(configurationProperties.getRedisPool().getMaxActive());
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMinEvictableIdleTimeMillis(configurationProperties.getRedisPool().getMinEvictableIdleTime());
        poolConfig.setTimeBetweenEvictionRunsMillis(configurationProperties.getRedisPool().getMinEvictableIdleTime());
        return poolConfig;
    }

/*
    //uncomment the jedis dependency in pom
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration(), jedisClientConfiguration());
        return jedisConnectionFactory;
    }

    private JedisClientConfiguration jedisClientConfiguration() {
        JedisClientConfiguration jedisClientConfiguration
                = JedisClientConfiguration.builder()
                .connectTimeout(Duration.ofSeconds(1))
                .readTimeout(Duration.ofSeconds(1))
                .usePooling()
                .poolConfig(buildPoolConfig())
                .build();
        return jedisClientConfiguration;
    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(50);
        poolConfig.setMaxIdle(50);
        poolConfig.setMinIdle(10);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(5).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(5).toMillis());
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxWaitMillis(5000);
        return poolConfig;
    }*/
}
