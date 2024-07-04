package com.w2.springtemplate.infrastructure.repository;

import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.entities.SysRole;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleRepository extends BaseJpaRepository<SysRole, String> {
}
