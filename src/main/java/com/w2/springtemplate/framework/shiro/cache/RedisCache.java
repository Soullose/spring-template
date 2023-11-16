package com.w2.springtemplate.framework.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

@Slf4j
public class RedisCache<K, V> implements Cache<K, V> {



    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object get(Object key) throws CacheException {
        log.info("get key:{}", (String) key);
//        if (Boolean.FALSE.equals(redisTemplate.hasKey((String) key))) {
//            return null;
//        }
        return redisTemplate.opsForValue().get((String) key);
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        log.info("put key:{}", (String) key);
        redisTemplate.opsForValue().set((String) key, value);
        return value;
    }

    @Override
    public Object remove(Object key) throws CacheException {
        Object o;
        if (Boolean.FALSE.equals(redisTemplate.hasKey((String) key))) {
            return null;
        } else {
            o = redisTemplate.opsForValue().get((String) key);
            redisTemplate.opsForValue().getOperations().delete((String) key);
        }
        return o;
    }

    @Override
    public void clear() throws CacheException {
//        Set<String> keys = redisTemplate.keys("*" + cacheKey + "*");
//        redisTemplate.delete(keys);
    }

    @Override
    public int size() {
//        Set<String> keys = redisTemplate.keys("*" + cacheKey + "*");
        return 0;
    }

    @Override
    public Set keys() {
//        Set<String> keys = redisTemplate.keys("*" + cacheKey + "*");
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }
}
