package com.w2.springtemplate.infrastructure.repository;

import com.w2.springtemplate.domain.model.user.SysUserService;
import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.model.dto.SysUserRegDTO;

public interface SysUserRepository extends BaseJpaRepository<SysUser, String>, SysUserService {


    default SysUserRegDTO register(SysUserRegDTO user){


        return null;
    }
}
