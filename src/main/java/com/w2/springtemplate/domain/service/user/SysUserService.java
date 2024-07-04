package com.w2.springtemplate.domain.service.user;

import com.w2.springtemplate.domain.model.user.User;
import com.w2.springtemplate.domain.model.user.dto.RegisterUserDTO;
import com.w2.springtemplate.domain.model.user.dto.UpdateUserDTO;
import com.w2.springtemplate.infrastructure.entities.SysUser;

import java.util.List;

/// 领域事件
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

	/**
	 * 修改用户密码
	 * @param id			用户ID
	 * @param newPassword	用户新密码
	 * @return				{@link SysUser}
	 */
	SysUser changeSysUserPassword(String id,String newPassword);
}
