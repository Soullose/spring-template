package com.w2.springtemplate.domain.experimental;

import java.io.Serializable;

/**
 * 实验性-DDD-值对象
 * @param <T>
 */
public interface ValueObject<T> extends Serializable {

    /**
     * 值对象按其属性的值进行比较，它们没有标识。
     * @param other
     * @return
     */
    boolean sameValueAs(T other);
}
