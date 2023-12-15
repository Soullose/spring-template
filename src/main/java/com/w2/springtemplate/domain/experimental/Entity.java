package com.w2.springtemplate.domain.experimental;

/**
 * 实验性-DDD-实体
 * @param <T>
 */
public interface Entity<T>{

    /**
     * 实体按标识进行比较
     * @param other
     * @return
     */
    boolean sameIdentityAs(T other);
}
