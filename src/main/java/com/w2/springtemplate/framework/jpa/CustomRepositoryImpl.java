package com.w2.springtemplate.framework.jpa;

import java.util.Optional;
import java.util.function.Function;


import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.query.FluentQuery;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class CustomRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomRepository<T, ID> {

	private final JPAQueryFactory queryFactory;
	private final QuerydslJpaPredicateExecutor<T> querydslJpaPredicateExecutor;

	public CustomRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		// 使用正确的构造函数参数
		EntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
		this.queryFactory = new JPAQueryFactory(entityManager);
		this.querydslJpaPredicateExecutor = new QuerydslJpaPredicateExecutor<>(entityInformation, entityManager,
				resolver, null) {
		};
	}

	@Override
	public JPAQueryFactory getJPAQueryFactory() {
		return queryFactory;
	}

	// 实现 QuerydslPredicateExecutor 接口的方法
	@Override
	public Optional<T> findOne(Predicate predicate) {
		return querydslJpaPredicateExecutor.findOne(predicate);
	}

	@Override
	public Iterable<T> findAll(Predicate predicate) {
		return querydslJpaPredicateExecutor.findAll(predicate);
	}

	@Override
	public Iterable<T> findAll(Predicate predicate, Sort sort) {
		return querydslJpaPredicateExecutor.findAll(predicate, sort);
	}

	@Override
	public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
		return querydslJpaPredicateExecutor.findAll(predicate, orders);
	}

	@Override
	public Iterable<T> findAll(OrderSpecifier<?>... orders) {
		return querydslJpaPredicateExecutor.findAll(orders);
	}

	@Override
	public Page<T> findAll(Predicate predicate, Pageable pageable) {
		return querydslJpaPredicateExecutor.findAll(predicate, pageable);
	}

	@Override
	public long count(Predicate predicate) {
		return querydslJpaPredicateExecutor.count(predicate);
	}

	@Override
	public boolean exists(Predicate predicate) {
		return querydslJpaPredicateExecutor.exists(predicate);
	}

	@Override
	public <S extends T, R> R findBy(Predicate predicate,
			Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
		return querydslJpaPredicateExecutor.findBy(predicate, queryFunction);
	}

}
