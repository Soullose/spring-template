package com.w2.springtemplate.infrastructure.entities;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.w2.springtemplate.framework.jpa.BaseEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "t_sys_role")
@Table(name = "t_sys_role")
@Comment("系统角色表")
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
	@JoinTable(name = "t_user_role", joinColumns = {@JoinColumn(name = "role_id_")}, inverseJoinColumns = {
			@JoinColumn(name = "user_id_")})
	private Set<SysUser> users = Sets.newHashSet();

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "t_role_permission", joinColumns = {@JoinColumn(name = "role_id_")}, inverseJoinColumns = {
			@JoinColumn(name = "permission_id_")})
	private Set<SysPermission> permissions = Sets.newHashSet();
}
