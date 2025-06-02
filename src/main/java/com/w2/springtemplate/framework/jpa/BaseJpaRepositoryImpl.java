package com.w2.springtemplate.framework.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;



//public abstract class BaseJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
//		implements
//			BaseJpaRepository<T, ID> {
//
//	private final EntityManager em;
//
//	protected final JPAQueryFactory queryFactory;
//
//	public BaseJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
//		super(domainClass, em);
//		this.em = em;
//		this.queryFactory = new JPAQueryFactory(em);
//	}
//
//	public JPAQueryFactory getQueryFactory() {
//		return this.queryFactory;
//	}
//}
