package com.w2.springtemplate.test;

import com.w2.springtemplate.framework.jpa.HighConcurrentSnowflake;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

@Slf4j
public class SnowFlakeTest1 {
//    private static final HighConcurrentSnowflake ID_GENERATOR = new HighConcurrentSnowflake();

    public static void main(String[] args) {
        // 单线程生成
        long id1 = HighConcurrentSnowflake.getInstance().nextId();;

        // 并发生成
        IntStream.range(0, 10_000_000).parallel().forEach(i -> {
            long id = HighConcurrentSnowflake.getInstance().nextId();
            // 使用ID...
            log.debug("id: {}", id);
        });
    }
}
