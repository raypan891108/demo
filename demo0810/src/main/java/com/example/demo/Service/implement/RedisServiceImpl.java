package com.example.demo.Service.implement;

import com.example.demo.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //private final HashOperations<String, String, String> hashOperations;



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
