package com.w2.springtemplate.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.w2.springtemplate.framework.jpa.CustomRepositoryFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
@EnableJpaAuditing()
@EnableTransactionManagement
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class, basePackages = {
		"com.w2.springtemplate.infrastructure.repository" })
public class JpaConfiguration {
	private static final Logger log = LoggerFactory.getLogger(JpaConfiguration.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Bean
	public JPAQueryFactory queryFactory() {
		log.debug("创建JPAQueryFactory");
		return new JPAQueryFactory(entityManager);
	}
}
