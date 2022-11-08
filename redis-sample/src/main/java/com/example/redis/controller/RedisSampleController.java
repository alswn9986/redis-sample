package com.example.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.service.RedisSampleService;

@RestController
public class RedisSampleController {
    
    @Autowired
    private RedisSampleService redisSampleService;
 
    @PostMapping(value = "/getRedisStringValue")
    public String getRedisStringValue(String key) {
        return redisSampleService.getRedisStringValue(key);
    }
    
    @PostMapping(value = "/setRedisStringValue")
    public void setRedisStringValue(String key, String value) {
    	redisSampleService.setRedisStringValue(key, value);
    }
}