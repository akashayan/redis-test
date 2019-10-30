package com.akash.github.redisexample.service.pubsub;

import com.akash.github.redisexample.enums.RedisKeyEvent;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.util.ObjectUtils;

public class RedisListener extends KeyspaceEventMessageListener {

    private RedisMessageListenerContainer listenerContainer;

    private String patternTopic;

    public RedisListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    public void setListenerContainer(RedisMessageListenerContainer listenerContainer) {
        this.listenerContainer = listenerContainer;
    }

    public void setPatternTopic(String patternTopic) {
        this.patternTopic = patternTopic;
    }

    @Override
    public void setKeyspaceNotificationsConfigParameter(String keyspaceNotificationsConfigParameter) {
        super.setKeyspaceNotificationsConfigParameter(keyspaceNotificationsConfigParameter);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        if (message != null && !ObjectUtils.isEmpty(message.getChannel()) && !ObjectUtils.isEmpty(message.getBody())) {
            String event = new String(message.getBody());
            if(RedisKeyEvent.EXPIRED.getValue().equals(event)) {
                System.out.println("Message Body: " + new String(message.getBody()) + " Channel : " + new String(message.getChannel()));
                this.doHandleMessage(message);
            }
        }
    }

    @Override
    protected void doHandleMessage(Message message) {
        String body = new String(message.getChannel());
        String[] array = body.split(":");
        String key = array[1];
        System.out.println("Key : " + key);
    }

    @Override
    protected void doRegister(RedisMessageListenerContainer container) {
        this.listenerContainer.addMessageListener(this, new PatternTopic(patternTopic));
    }
}
