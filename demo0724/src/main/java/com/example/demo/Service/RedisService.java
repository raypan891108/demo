package com.example.demo.Service;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    //private final HashOperations<String, String, String> hashOperations;


    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        //this.hashOperations = redisTemplate.opsForHash();
    }
    
    public void saveDataToRedis(String key, String value, long exp) {

        //set(key, token, 時效, 秒為單位)
        redisTemplate.opsForValue().set(key, value, exp, TimeUnit.SECONDS);
        
    }
    //用key獲取Data
    public String getDataFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteDataFromRedis(String key) {
        redisTemplate.delete(key);
    }



}