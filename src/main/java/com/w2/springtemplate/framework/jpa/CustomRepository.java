package com.w2.springtemplate.framework.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomRepository<T, ID> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {
    JPAQueryFactory getJPAQueryFactory();

}
