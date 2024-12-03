package com.oasys.security.jwt;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
@Component
public class RedisUtil {
	@Autowired
    public RedisTemplate<String, String> redisTemplate;

	

    /**
     *redis Save data and set cache time
     * @param key key
     * @param value value
     * @param time second
     */
    public void set(String key,String value,long time){
        redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.MINUTES);
    }


    /**
     * redis Store data
     * @param key key
     * @param value value
     */
    public void set(String key,String value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Get expiration time according to key
     * @param key
     * @return
     */
    public Long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * Determine whether the key exists
     * @param key
     * @return
     */
    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * Set the expiration time according to the key
     * @param key key
     * @param time second
     * @return
     */
    public Boolean expire(String key,long time){
        return redisTemplate.expire(key,time , TimeUnit.SECONDS);
    }
    
    public String getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }
    
    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }
}
