package com.w2.springtemplate.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.entities.SysTenant;

/**
 * @apiNote 租户信息
 * @author wsfzj 2024/7/16
 * @version 1.0
 */
@Repository
public interface SysTenantRepository extends BaseJpaRepository<SysTenant, String> {
}
