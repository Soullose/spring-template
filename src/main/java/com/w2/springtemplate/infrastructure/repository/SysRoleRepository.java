package com.w2.springtemplate.infrastructure.repository;

import com.w2.springtemplate.domain.model.role.SysRoleService;
import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.entities.SysRole;

public interface SysRoleRepository extends BaseJpaRepository<SysRole, String> , SysRoleService {
}
