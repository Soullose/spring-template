package com.w2.springtemplate.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.entities.SysUserRole;
import com.w2.springtemplate.infrastructure.entities.SysUserRoleId;

/**
 * @apiNote 用户角色表
 * @author wsfzj 2024/7/16
 * @version 1.0
 */
@Repository
public interface SysUserRoleRepository extends BaseJpaRepository<SysUserRole, SysUserRoleId> {
}
