package com.w2.springtemplate.framework.jpa;

import java.io.Serializable;
import java.util.SplittableRandom;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.github.f4b6a3.uuid.factory.standard.TimeOrderedEpochFactory;

/// 默认主键生成器
public class IdGenerator implements IdentifierGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		SplittableRandom random = new SplittableRandom();
        TimeOrderedEpochFactory factory = TimeOrderedEpochFactory.builder().withIncrementPlus1()
                .withSafeRandom()
                .withRandomFunction(random::nextLong).build();
		return factory.create().toString();
	}

}
