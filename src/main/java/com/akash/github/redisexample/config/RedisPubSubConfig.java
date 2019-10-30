package com.akash.github.redisexample.config;

import com.akash.github.redisexample.service.pubsub.RedisListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class RedisPubSubConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean("redisMessageListenerContainer")
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        return redisMessageListenerContainer;
    }

/*    @Bean("redisKeyExpirationListener")
    public RedisKeyExpirationListener redisKeyExpirationListener() {
        String keyPrefix = "REG";
        RedisKeyExpirationListener redisKeyExpirationListener = new RedisKeyExpirationListener(redisMessageListenerContainer(), keyPrefix);
        return redisKeyExpirationListener;
    }*/

    @Bean("redisListener")
    public RedisListener redisListener() {
        RedisListener redisListener = new RedisListener(redisMessageListenerContainer());
        redisListener.setListenerContainer(redisMessageListenerContainer());
        redisListener.setKeyspaceNotificationsConfigParameter("Ex");
        redisListener.setPatternTopic("__keyspace@*__:REG*");
        return redisListener;
    }
}
