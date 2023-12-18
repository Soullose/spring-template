package com.w2.springtemplate.domain.model.user;

import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.model.dto.RegisterUserDTO;
import com.w2.springtemplate.model.dto.UpdateUserDTO;

import java.util.List;

public interface SysUserService {

	/// 注册
	SysUser register(RegisterUserDTO user);

	/// 更新用户
	SysUser update(UpdateUserDTO user);

	/// 查找所有用户
	List<User> findAllUser();
}
