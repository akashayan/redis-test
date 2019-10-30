package com.akash.github.redisexample.service.pubsub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * This listener is used to listen to expired events of keys starting with a prefix
 */
public class RedisListener extends KeyspaceEventMessageListener {

    private RedisMessageListenerContainer listenerContainer;

    private String patternTopic;

    private String keyspaceNotificationsConfigParameter;

    public RedisListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
        this.listenerContainer = listenerContainer;
    }

    public void setPatternTopic(String patternTopic) {
        this.patternTopic = patternTopic;
    }

    @Override
    public void setKeyspaceNotificationsConfigParameter(String keyspaceNotificationsConfigParameter) {
        this.keyspaceNotificationsConfigParameter = keyspaceNotificationsConfigParameter;
    }

    @Override
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

        this.doRegister(this.listenerContainer);
    }


    @Override
    protected void doRegister(RedisMessageListenerContainer container) {
        this.listenerContainer.addMessageListener(this, new PatternTopic(patternTopic));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        doHandleMessage(message);
    }

    @Override
    protected void doHandleMessage(Message message) {
        String body = new String(message.getChannel());
        String[] array = body.split(":");
        String key = array[1];
        System.out.println("Key : " + key);
    }

}
