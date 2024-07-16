package com.w2.springtemplate.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.entities.SysOrg;

@Repository
public interface SysOrgRepository extends BaseJpaRepository<SysOrg, String> {
}
