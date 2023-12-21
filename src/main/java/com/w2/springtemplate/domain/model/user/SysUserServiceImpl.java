package com.w2.springtemplate.domain.model.user;

import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.domain.model.user.dto.UpdateUserDTO;
import com.w2.springtemplate.infrastructure.entities.SysUser;

import java.util.List;

public class SysUserServiceImpl implements SysUserService{
    @Override
    public SysUser register(RegisterUserDTO user) {
        return null;
    }

    @Override
    public SysUser update(UpdateUserDTO user) {
        return null;
    }

    @Override
    public SysUser findOneByUsername(String username) {
        return null;
    }

    @Override
    public List<User> findAllUser() {
        return null;
    }
}
