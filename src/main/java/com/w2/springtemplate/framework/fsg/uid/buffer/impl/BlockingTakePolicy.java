package com.w2.springtemplate.framework.fsg.uid.buffer.impl;

import com.w2.springtemplate.framework.fsg.uid.buffer.RejectedTakeBufferHandler;
import com.w2.springtemplate.framework.fsg.uid.buffer.RingBuffer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

public class BlockingTakePolicy implements RejectedTakeBufferHandler {
    private final long timeoutNs;

    public BlockingTakePolicy(long timeout, TimeUnit unit) {
        this.timeoutNs = unit.toNanos(timeout);
    }

    @Override
    public void rejectTakeBuffer(RingBuffer ringBuffer) {
        final long deadline = System.nanoTime() + timeoutNs;
        while (ringBuffer.isEmpty()) {
            if (System.nanoTime() > deadline) {
                try {
                    throw new TimeoutException("Take operation timed out");
                } catch (TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
            LockSupport.parkNanos(10_000); // 10μs等待
        }
    }
}