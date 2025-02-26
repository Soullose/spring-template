package com.w2.springtemplate.framework.fsg.uid.config;

//import com.w2.springtemplate.framework.fsg.uid.utils.DateUtils;

import com.w2.springtemplate.framework.fsg.uid.impl.CachedUidGenerator;
import com.w2.springtemplate.framework.fsg.uid.impl.DefaultUidGenerator;
import com.w2.springtemplate.framework.fsg.uid.worker.DisposableWorkerIdAssigner;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UIDGenConfiguration {

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
        cachedUidGenerator.setTimeBits(31);
        cachedUidGenerator.setWorkerBits(23);
        cachedUidGenerator.setSeqBits(9);
        cachedUidGenerator.setEpochStr("2025-02-20");
        cachedUidGenerator.setBoostPower(3);
        cachedUidGenerator.setScheduleInterval(60L);
        cachedUidGenerator.setRejectedPutBufferHandler((ringBuffer, uid) -> {
        });
        cachedUidGenerator.setRejectedTakeBufferHandler((ringBuffer) -> {
        });
        return cachedUidGenerator;
    }

    @Bean(name = "defaultUidGenerator")
    public DefaultUidGenerator defaultUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
        DefaultUidGenerator defaultUidGenerator = new DefaultUidGenerator();
        defaultUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        defaultUidGenerator.setTimeBits(31);
        defaultUidGenerator.setWorkerBits(23);
        defaultUidGenerator.setSeqBits(9);
        defaultUidGenerator.setEpochStr("2025-02-20");
        return defaultUidGenerator;
    }
}
