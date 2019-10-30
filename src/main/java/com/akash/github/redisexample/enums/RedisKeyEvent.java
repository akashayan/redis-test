package com.akash.github.redisexample.enums;

import lombok.Getter;

@Getter
public enum RedisKeyEvent {

    SET("set"),
    EXPIRE("expire"),
    EXPIRED("expired");

    private String value;

    RedisKeyEvent(String value) {
        this.value = value;
    }
}
