package com.w2.springtemplate.framework.encrypt.gm.sm4;

import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.IOException;

public class SM4CipherPool extends GenericObjectPool<SM4Cipher> {
    private static final SM4CipherFactory sm4CipherFactory = new SM4CipherFactory();

    public SM4CipherPool(int max) {
        this(1, max);
    }

    public SM4CipherPool(int init, int max) {
        super(sm4CipherFactory);
        setMaxTotal(max);
        setMinIdle(init);
    }

    public SM4CipherPool(SM4PoolConfig config) {
        super(sm4CipherFactory, config);
    }

    public SM4CipherPool() throws IOException {
        super(sm4CipherFactory, new SM4PoolConfig());
    }
}
