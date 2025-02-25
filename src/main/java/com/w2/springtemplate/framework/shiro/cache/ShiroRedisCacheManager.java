package com.w2.springtemplate.framework.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

@Deprecated(forRemoval = true)
@Slf4j
public class ShiroRedisCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.info("RedisCacheManager-getCache: {}", name);
        return new RedisCache<K, V>();
    }
}
