package com.w2.springtemplate.framework.fsg.uid.buffer.impl;

import com.w2.springtemplate.framework.fsg.uid.buffer.RejectedTakeBufferHandler;
import com.w2.springtemplate.framework.fsg.uid.buffer.RingBuffer;

import java.io.Serializable;
import java.util.concurrent.locks.LockSupport;

public class FallbackTakeHandler implements RejectedTakeBufferHandler {
    private final long fallbackUid;
    private final int maxRetries;

    public FallbackTakeHandler(long fallbackUid, int maxRetries) {
        this.fallbackUid = fallbackUid;
        this.maxRetries = maxRetries;
    }

    @Override
    public void rejectTakeBuffer(RingBuffer ringBuffer) {
        int retry = 0;
        while (retry++ < maxRetries && ringBuffer.isEmpty()) {
            LockSupport.parkNanos(10_000); // 10μs
        }
        if (ringBuffer.isEmpty()) {
            ringBuffer.put(fallbackUid);
        } else {
            ringBuffer.take();
        }
    }
}
