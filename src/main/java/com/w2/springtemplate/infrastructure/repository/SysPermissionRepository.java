package com.w2.springtemplate.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.entities.SysPermission;

@Repository
public interface SysPermissionRepository extends BaseJpaRepository<SysPermission, String> {
}
