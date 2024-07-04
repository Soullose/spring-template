package com.w2.springtemplate.interfaces.user.facade.internal;

import com.w2.springtemplate.domain.service.user.SysUserService;
import com.w2.springtemplate.infrastructure.repository.SysUserRepository;
import com.w2.springtemplate.interfaces.user.facade.SysUserServiceFacade;

public class SysUserServiceFacadeImpl implements SysUserServiceFacade {

	/// 领域层
	private final SysUserService sysUserService;

	/// 仓储层
	private final SysUserRepository sysUserRepository;

	public SysUserServiceFacadeImpl(SysUserRepository sysUserRepository,SysUserService sysUserService) {
		this.sysUserService = sysUserService;
		this.sysUserRepository = sysUserRepository;
	}
}
