package com.w2.springtemplate.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.querydsl.jpa.impl.JPAProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Configuration
@EnableJpaAuditing()
@EnableTransactionManagement
// @EnableJpaRepositories(repositoryFactoryBeanClass =
// CustomRepositoryFactoryBean.class, basePackages = {
// "com.w2.springtemplate.infrastructure.repository" })
public class JpaConfiguration {
	private static final Logger log = LoggerFactory.getLogger(JpaConfiguration.class);

	// @PersistenceContext
	// private EntityManager entityManager;

	@Bean
	public JPAQueryFactory queryFactory(EntityManager entityManager) {
		log.debug("创建JPAQueryFactory");
		return new JPAQueryFactory(JPAProvider.getTemplates(entityManager), entityManager);
		// return new JPAQueryFactory(entityManager);
	}
}
