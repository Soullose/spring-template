package com.w2.springtemplate.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.entities.SysUserGroup;

@Repository
public interface SysUserGroupRepository extends BaseJpaRepository<SysUserGroup, String> {
}
