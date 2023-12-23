package com.w2.springtemplate.framework.vfs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.ProtocolResolver;

@Slf4j
public class ApacheVfsApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {


    private static final ApacheVfsInitializer apacheVfsInitializer = new ApacheVfsInitializer();

    /**
     * 协议解析器
     */
    public ProtocolResolver newProtocolResolver() {
        return new ApacheVfsProtocolResolver();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        apacheVfsInitializer.init();
        if (log.isDebugEnabled()){
            log.debug("ApacheVfsProtocolResolver");
        }
        // 添加虚拟文件协议分解器
        applicationContext.addProtocolResolver(newProtocolResolver());

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
