package com.w2.springtemplate.infrastructure.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.w2.springtemplate.framework.jpa.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_sys_role")
@Entity
public class SysRole extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4015033813412866557L;
	/// 角色编号
	@Column(name = "role_code_")
	private String roleCode;

	/// 角色名称
	@Column(name = "role_name_")
	private String roleName;

	/// 备注
	@Column(name = "remark_")
	private String remark;

	@ManyToMany
	@JoinColumn(name = "user_id_", referencedColumnName = "id_",nullable = false)
	private Set<SysUser> sysUsers;
}
