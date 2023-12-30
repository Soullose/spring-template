package com.w2.springtemplate.framework.encrypt.gm.sm4;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * from https://github.com/Hyperledger-TWGC/java-gm/blob/master/src/main/java/twgc/gm/pool/SM4CipherFactory.java
 */
public class SM4CipherFactory extends BasePooledObjectFactory<SM4Cipher> {
    /**
     * Creates an object instance, to be wrapped in a {@link PooledObject}.
     * <p>This method <strong>must</strong> support concurrent, multi-threaded
     * activation.</p>
     *
     * @return an instance to be served by the pool
     * @throws Exception if there is a problem creating a new instance,
     *                   this will be propagated to the code requesting an object.
     */
    @Override
    public SM4Cipher create() throws Exception {
        return new SM4Cipher();
    }

    /**
     * Wraps the provided instance with an implementation of
     * {@link PooledObject}.
     *
     * @param obj the instance to wrap
     * @return The provided instance, wrapped by a {@link PooledObject}
     */
    @Override
    public PooledObject<SM4Cipher> wrap(SM4Cipher obj) {
        return new DefaultPooledObject<>(obj);
    }
}
