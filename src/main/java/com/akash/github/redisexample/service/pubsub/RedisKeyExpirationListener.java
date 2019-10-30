package com.akash.github.redisexample.service.pubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

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

/*    @Override
    public void init() {
        if (StringUtils.hasText(this.keyspaceNotificationsConfigParameter)) {
            RedisConnection connection = this.listenerContainer.getConnectionFactory().getConnection();
            try {
                Properties config = connection.getConfig("notify-keyspace-events");
                if (!StringUtils.hasText(config.getProperty("notify-keyspace-events"))) {
                    connection.setConfig("notify-keyspace-events", this.keyspaceNotificationsConfigParameter);
                }
            } finally {
                connection.close();
            }
        }

        doRegister(this.listenerContainer);
    }

    protected void doRegister(RedisMessageListenerContainer container) {
        this.listenerContainer.addMessageListener(this, TOPIC_ALL_KEYEVENTS);
    }*/
//extends KeyspaceEventMessageListener {

    /*public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void setKeyspaceNotificationsConfigParameter(String keyspaceNotificationsConfigParameter) {
        super.setKeyspaceNotificationsConfigParameter(keyspaceNotificationsConfigParameter);
    }

    @Override
    protected void doHandleMessage(Message message) {
        String expiredKey = new String(message.getBody());
        System.out.println("Expired key : " + expiredKey);
    }*/
//implements MessageListener {

/*    @Override
    public void onMessage(Message message, byte[] bytes) {
        String expiredKey = new String(message.getBody());
        System.out.println("Expired key : " + expiredKey);
    }*/
//}
