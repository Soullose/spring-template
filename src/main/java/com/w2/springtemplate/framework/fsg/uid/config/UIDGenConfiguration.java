package com.w2.springtemplate.framework.fsg.uid.config;

//import com.w2.springtemplate.framework.fsg.uid.utils.DateUtils;

import com.w2.springtemplate.framework.fsg.uid.buffer.impl.BlockingTakePolicy;
import com.w2.springtemplate.framework.fsg.uid.impl.CachedUidGenerator;
import com.w2.springtemplate.framework.fsg.uid.impl.DefaultUidGenerator;
import com.w2.springtemplate.framework.fsg.uid.worker.DisposableWorkerIdAssigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

//@Configuration
public class UIDGenConfiguration {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public DisposableWorkerIdAssigner disposableWorkerIdAssigner() {
        return new DisposableWorkerIdAssigner();
    }

    @Qualifier
    @Bean(name = "cachedUidGenerator")
    public CachedUidGenerator cachedUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        //以下为可选配置, 如未指定将采用默认值
        cachedUidGenerator.setTimeBits(41);
        cachedUidGenerator.setWorkerBits(10);
        cachedUidGenerator.setSeqBits(12);
        cachedUidGenerator.setEpochStr("2025-02-20 01:01:01.111");
        cachedUidGenerator.setBoostPower(3);
//        cachedUidGenerator.setScheduleInterval(90L);
        cachedUidGenerator.setRejectedPutBufferHandler((ringBuffer, uid) -> {
        });
        cachedUidGenerator.setRejectedTakeBufferHandler(new BlockingTakePolicy(500, TimeUnit.MILLISECONDS));
        return cachedUidGenerator;
    }

    @Bean(name = "defaultUidGenerator")
    public DefaultUidGenerator defaultUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
        DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator();
        defaultUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        defaultUidGenerator.setTimeBits(41);
        defaultUidGenerator.setWorkerBits(10);
        defaultUidGenerator.setSeqBits(12);
        defaultUidGenerator.setEpochStr("2025-02-20 01:01:01.111");
        return defaultUidGenerator;
    }
}
