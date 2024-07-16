package com.w2.springtemplate.infrastructure.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Access;

import lombok.Data;

/**
 * @author wsfzj 2024/7/16
 * @version 1.0
 * @description 用户角色绑定表
 */
@Data
@Entity
@Table(name = "t_user_role")
@IdClass(SysUserRoleId.class)
public class SysUserRole implements Serializable {
	private static final long serialVersionUID = -6713253701482698959L;

	@Id
	@Access(AccessType.PROPERTY)
	@Column(name = "user_id_")
	private String userId;

	@Id
	@Access(AccessType.PROPERTY)
	@Column(name = "role_id_")
	private String roleId;

	/// 是否激活
	@Column(name = "activate_")
	private boolean activate = false;
}
