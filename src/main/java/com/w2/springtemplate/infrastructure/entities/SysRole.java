package com.w2.springtemplate.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.w2.springtemplate.framework.jpa.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "role_id_") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id_") })
	private Set<SysUser> users;
}
