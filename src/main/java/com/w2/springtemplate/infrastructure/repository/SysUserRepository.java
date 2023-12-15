package com.w2.springtemplate.infrastructure.repository;

import com.w2.springtemplate.domain.model.user.SysUserService;
import com.w2.springtemplate.domain.model.user.User;
import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.converters.SysUserConverter;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.model.dto.SysUserRegDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface SysUserRepository extends BaseJpaRepository<SysUser, String>, SysUserService {


    default SysUserRegDTO register(SysUserRegDTO user){


        return null;
    }

    default List<User> findAllUser() {
        List<SysUser> all = this.findAll();
        return all.stream().map(SysUserConverter.INSTANCE::fromPO).collect(Collectors.toList());
    }
}
