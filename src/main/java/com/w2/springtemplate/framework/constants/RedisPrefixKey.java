package com.w2.springtemplate.framework.constants;

/**
 * Redis-PrefixKey接口定义了Redis中使用的所有前缀键。
 */
public interface RedisPrefixKey {
    public static final String SYSTEM_SHIRO_AUTH_LOCK = "system:shiro:auth:lock:";
    public static final String SYSTEM_SHIRO_AUTH_USERINFO = "system:shiro:auth:userinfo:";
}
