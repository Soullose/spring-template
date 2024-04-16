package com.w2.springtemplate.framework.shiro.extention;

import java.util.concurrent.TimeUnit;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

import com.w2.springtemplate.framework.constants.RedisPrefixKey;
import com.w2.springtemplate.utils.crypto.RedisUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RetryPasswordCredentialsMatcher extends PasswordMatcher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 时间间隔（分钟）
     */
    private static final int TIME_INTERVAL = 1;

    /**
     * 登录失败重试次数上限
     */
    private static final int FAILED_RETRY_TIMES = 5;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        int lockedNumber = 0;
        RedisAtomicInteger counter = getRedisCounter(RedisPrefixKey.SYSTEM_SHIRO_AUTH_LOCK + username);
        if (RedisUtils.hasKey(RedisPrefixKey.SYSTEM_SHIRO_AUTH_LOCK + username)) {
            log.debug("current:{}", counter.get());
            lockedNumber = counter.get();
            log.debug("RetryPasswordCredentialsMatcher1:{}", lockedNumber);
            if (lockedNumber >= FAILED_RETRY_TIMES) {
                log.info("用户 {} 登录失败次数过多，已被锁定", username);
                throw new LockedAccountException("账户被锁定,请于5分后重新登录");
            }
        }
        boolean match = super.doCredentialsMatch(token, info);
        if (!match) {
            counter.getAndIncrement();
        }
        return match;
    }

    private RedisAtomicInteger getRedisCounter(String key) {
        RedisAtomicInteger counter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        if (counter.get() == 0) {
            // 设置过期时间，5分钟
            counter.expire(TIME_INTERVAL, TimeUnit.MINUTES);
        }
        return counter;
    }

}
