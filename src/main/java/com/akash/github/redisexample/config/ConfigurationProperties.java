package com.akash.github.redisexample.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationProperties {

    private Redis redis;

    private RedisPool redisPool;

    @Getter
    @Setter
    public static class Redis {
        private String hostName;
        private int port;
    }

    @Getter
    @Setter
    public static class RedisPool {
        private long timeout;
        private int maxIdle;
        private int minIdle;
        private long maxWaitMillis;
        private int maxActive;
        private long minEvictableIdleTime;
        private long timeBetweenEvictionRuns;
    }
}
