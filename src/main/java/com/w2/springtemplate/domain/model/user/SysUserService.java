package com.w2.springtemplate.domain.model.user;

import com.w2.springtemplate.model.dto.SysUserRegDTO;

public interface SysUserService {

    ///注册
    SysUserRegDTO register(SysUserRegDTO user);
}
