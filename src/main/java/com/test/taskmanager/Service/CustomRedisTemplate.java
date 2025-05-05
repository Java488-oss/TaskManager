package com.test.taskmanager.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.taskmanager.Config.RedisConfig;
import com.test.taskmanager.Model.TokenStatus;
import com.test.taskmanager.Model.User;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class CustomRedisTemplate {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private String timerParam;

    public CustomRedisTemplate(@Value("${jwt.timer.param}") String timerParam) {

        this.timerParam = timerParam;
    }

    public void save(String key, Object value) {
        try {
            String json = new ObjectMapper().writeValueAsString(value);
            redisTemplate.opsForValue().set(key, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveFromTtl(String key,  Object value) {
        try {
            int timeout = Integer.parseInt(timerParam.split(":")[0]);
            TimeUnit timeUnit = TimeUnit.valueOf(timerParam.split(":")[1].toUpperCase());
            String json = new ObjectMapper().writeValueAsString(value);

            redisTemplate.opsForValue().set(key, json);
            redisTemplate.expire(key, timeout, timeUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getValue(String key) {
        try {
            if(key != null) {
                TokenStatus tokenStatus = new ObjectMapper().readValue(redisTemplate.opsForValue().get(key).toString(), TokenStatus.class);

                switch (tokenStatus) {
                    case TokenStatus.ACTIVE:
                        System.out.println(tokenStatus);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return null;
    }

    public void remove(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



