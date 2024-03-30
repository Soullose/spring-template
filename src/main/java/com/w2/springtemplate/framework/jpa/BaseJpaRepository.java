package com.w2.springtemplate.framework.jpa;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;


@NoRepositoryBean
public interface BaseJpaRepository<T, ID> extends JpaRepositoryImplementation<T, ID>, QuerydslPredicateExecutor<T> {

//    EntityManager get

}
