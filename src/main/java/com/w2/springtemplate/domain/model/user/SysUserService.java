package com.w2.springtemplate.domain.model.user;

import com.w2.springtemplate.model.dto.SysUserRegDTO;

import java.util.List;

public interface SysUserService {

    ///注册
    SysUserRegDTO register(SysUserRegDTO user);

    List<User> findAllUser();
}
