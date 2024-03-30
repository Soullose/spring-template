package com.w2.springtemplate.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing()
@EnableTransactionManagement
public class JpaConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JpaConfiguration.class);
}
