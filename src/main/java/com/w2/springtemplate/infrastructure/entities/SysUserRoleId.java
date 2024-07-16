package com.w2.springtemplate.infrastructure.entities;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wsfzj 2024/7/16
 * @version 1.0
 * @description 用户角色绑定表主键
 */
@Data
public class SysUserRoleId implements Serializable {

	private static final long serialVersionUID = -7910686327168564L;

	private String userId;

	private String roleId;
}
