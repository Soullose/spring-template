package com.w2.springtemplate.config;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
@EnableJpaAuditing()
@EnableTransactionManagement
public class JpaConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JpaConfiguration.class);

    @Bean
    public JPAQueryFactory queryFactory(EntityManager em) {
        log.debug("创建JPAQueryFactory");
        return new JPAQueryFactory(em);
    }
}
