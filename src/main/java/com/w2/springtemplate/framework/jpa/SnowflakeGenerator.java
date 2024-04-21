package com.w2.springtemplate.framework.jpa;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import cn.hutool.core.util.IdUtil;

/// 基于Hutool的雪花算法实现
public class SnowflakeGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        return IdUtil.getSnowflakeNextIdStr();
    }

}
