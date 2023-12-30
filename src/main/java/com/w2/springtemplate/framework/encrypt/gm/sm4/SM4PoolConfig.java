package com.w2.springtemplate.framework.encrypt.gm.sm4;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

public class SM4PoolConfig extends GenericObjectPoolConfig {
    public SM4PoolConfig() {
        this.setMaxTotal(10);
        this.setMaxIdle(8);
        this.setMinIdle(2);
        this.setMaxWait(Duration.ofMillis(1000));
    }
}
