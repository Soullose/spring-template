package com.w2.springtemplate.framework.jpa;

import java.io.Serializable;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.w2.springtemplate.framework.fsg.uid.UidGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//import cn.hutool.core.util.IdUtil;

/// 基于neural 的雪花算法实现
public class SnowflakeGenerator implements IdentifierGenerator {

//    @Qualifier("cachedUidGenerator")
//    @Resource
//    private final UidGenerator uidGenerator;
//
//    public SnowflakeGenerator(@Qualifier("cachedUidGenerator") UidGenerator uidGenerator) {
//        this.uidGenerator = uidGenerator;
//    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
//        HighConcurrentSnowflake ID_GENERATOR = new HighConcurrentSnowflake();
//        return Long.toString(ID_GENERATOR.nextId());
//        SpringUtil.getBean(UidGenerator.class).getUID()
//        return String.valueOf(SpringUtil.getBean("cachedUidGenerator",UidGenerator.class).getUID());

        return String.valueOf(SpringUtil.getBean("defaultUidGenerator",UidGenerator.class).getUID());
//        return String.valueOf(HighConcurrentSnowflake.getInstance().nextId());
//        return IdUtil.fastSimpleUUID();
//        return SnowflakeIdGenerator.getInstance().nextIdStr();
    }

}
