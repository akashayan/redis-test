package com.akash.github.redisexample.service.pubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


/**
 * This listener is used to listen to all expired keys and then filters keys using prefix
 *
 */
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    /**
     * This is the prefix of the key to filter out
     */
    private String keyPrefix;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer, String keyPrefix) {
        super(listenerContainer);
        this.keyPrefix = keyPrefix;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody());
        if (expiredKey.startsWith(keyPrefix)) {
            System.out.println("Key expired : " + expiredKey);
        }

    }
}
