package com.example.demo.Service;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public interface RedisService {

    public void saveDataToRedis(String key, String value, long exp) ;
    //用key獲取Data
    public String getDataFromRedis(String key) ;

    public void deleteDataFromRedis(String key) ;


}