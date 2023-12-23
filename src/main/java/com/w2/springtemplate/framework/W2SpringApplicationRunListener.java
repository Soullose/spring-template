package com.w2.springtemplate.framework;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.w2.springtemplate.framework.vfs.ApacheVfsInitializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class W2SpringApplicationRunListener implements SpringApplicationRunListener {

    public W2SpringApplicationRunListener(SpringApplication application, String[] args) {}

    private static final ApacheVfsInitializer apacheVfsInitializer = new ApacheVfsInitializer();


    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        log.debug("111111111111111");
        apacheVfsInitializer.init();
        log.debug("2222222222222222");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        context.addProtocolResolver(apacheVfsInitializer.newProtocolResolver());
    }
}
