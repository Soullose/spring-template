package com.w2.springtemplate.domain.service.userGroup;

import com.w2.springtemplate.infrastructure.entities.SysUserGroup;

public interface SysUserGroupService {

	/// 创建用户组
	SysUserGroup createSysUserGroup(SysUserGroup sysUserGroup);

	/// 更新用户组
	SysUserGroup updateSysUserGroup(SysUserGroup sysUserGroup);

	/// 获取用户组
	SysUserGroup getSysUserGroupById(String id);

	/// 删除用户组
	void deleteSysUserGroup(String id);
}
