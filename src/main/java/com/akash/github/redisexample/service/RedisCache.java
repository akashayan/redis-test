package com.akash.github.redisexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCache<K, V> {

    @Autowired
    @Qualifier(value = "redisTemplate")
    private RedisTemplate<K, V> redisTemplate;

    public void set(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public V get(K key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setWithExpiry(K key, V value, Long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public void update(K key, V value) {
        Long expiry = redisTemplate.opsForValue().getOperations().getExpire(key);
        if(expiry > 0) {
            redisTemplate.opsForValue().set(key, value, expiry);
        }
    }

    public void delete(K key) {
        redisTemplate.delete(key);
    }
}
