package com.w2.springtemplate.framework.jpa;

import cn.hutool.extra.spring.SpringUtil;
import com.w2.springtemplate.framework.fsg.uid.UidGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/// 基于neural 的雪花算法实现
public class SnowflakeGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return String.valueOf(HighConcurrentSnowflake.getNextId());
//        return String.valueOf(new HighConcurrentSnowflake().nextId());
//        return String.valueOf(SpringUtil.getBean("cachedUidGenerator", UidGenerator.class).getUID());
    }

}
