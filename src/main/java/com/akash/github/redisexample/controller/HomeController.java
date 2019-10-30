package com.akash.github.redisexample.controller;

import com.akash.github.redisexample.model.Person;
import com.akash.github.redisexample.service.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/rest/redis")
public class HomeController {

    @Autowired
    private RedisCache<String, Object> cache;

    @GetMapping(value = "set")
    public void setValueInCache() {
        cache.setWithExpiry("REG1", new Person(000, "Ayan"), 3L, TimeUnit.SECONDS);
        cache.setWithExpiry("KEY", new Person(001, "Akash"), 3L, TimeUnit.SECONDS);
        cache.update("KEY", new Person(001, "John"));
        System.out.println("Successfully set value in cache");
    }

    @GetMapping(value = "get")
    public Person getValueFromCache() {
        return (Person) cache.get("key6");
    }
}
