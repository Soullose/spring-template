package com.w2.springtemplate.infrastructure.repository;

import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.domain.SysUser;

public interface SysUserRepository extends BaseJpaRepository<SysUser, String> {
}
