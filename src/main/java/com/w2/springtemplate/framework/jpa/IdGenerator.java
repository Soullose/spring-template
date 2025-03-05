package com.w2.springtemplate.framework.jpa;

import com.github.f4b6a3.uuid.factory.standard.TimeOrderedEpochFactory;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.SplittableRandom;

/// 默认主键生成器
public class IdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        SplittableRandom random = new SplittableRandom();
        TimeOrderedEpochFactory factory = new TimeOrderedEpochFactory(random::nextLong);
        return factory.create().toString();
    }


}
