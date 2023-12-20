package com.w2.springtemplate.domain.model.user;

import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.domain.model.user.dto.UpdateUserDTO;
import com.w2.springtemplate.infrastructure.entities.SysUser;

import java.util.List;

public interface SysUserService {

	/// 注册
	SysUser register(RegisterUserDTO user);

	/// 更新用户
	SysUser update(UpdateUserDTO user);

	/// 查找所有用户
	List<User> findAllUser();
}
