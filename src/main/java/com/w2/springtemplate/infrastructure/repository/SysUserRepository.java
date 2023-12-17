package com.w2.springtemplate.infrastructure.repository;

import com.w2.springtemplate.domain.model.user.SysUserService;
import com.w2.springtemplate.domain.model.user.User;
import com.w2.springtemplate.framework.jpa.BaseJpaRepository;
import com.w2.springtemplate.infrastructure.converters.SysUserConverter;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.model.dto.RegisterUserDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface SysUserRepository extends BaseJpaRepository<SysUser, String>, SysUserService {


    default SysUser register(RegisterUserDTO user){
        SysUser sysUser = SysUserConverter.INSTANCE.fromRegDTO(user);
        return save(sysUser);
    }

    default List<User> findAllUser() {
        List<SysUser> all = this.findAll();
        return all.stream().map(SysUserConverter.INSTANCE::fromPO).collect(Collectors.toList());
    }
}
