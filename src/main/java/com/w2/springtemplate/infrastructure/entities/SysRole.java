package com.w2.springtemplate.infrastructure.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.w2.springtemplate.framework.jpa.BaseEntity;

import lombok.Data;

@Data
@Table(name = "t_sys_role")
@Entity
public class SysRole extends BaseEntity implements Serializable {

	/// 角色编号
	@Column(name = "role_code_")
	private String roleCode;

	/// 角色名称
	@Column(name = "role_name_")
	private String roleName;

	/// 备注
	@Column(name = "remark_")
	private String remark;
}
