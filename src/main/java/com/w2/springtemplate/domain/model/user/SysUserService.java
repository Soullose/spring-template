package com.w2.springtemplate.domain.model.user;

import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.domain.model.user.dto.UpdateUserDTO;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import org.springframework.stereotype.Service;

import java.util.List;

/// 领域事件
@Service
public interface SysUserService {

	/// 注册
	SysUser register(RegisterUserDTO user);

	/// 更新用户
	SysUser update(UpdateUserDTO user);

	SysUser findOneByUsername(String username);

	/// 查找所有用户
	List<User> findAllUser();

	/**
	 * 重置密码
	 * @param id		用户ID
	 * @return			{@link SysUser}
	 */
	SysUser resetPassword(String id);
}
