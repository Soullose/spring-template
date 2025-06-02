package com.w2.springtemplate.infrastructure.entities;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.annotations.Comment;

import com.google.common.collect.Sets;
import com.w2.springtemplate.framework.jpa.BaseEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "t_permission")
@Table(name = "t_permission")
@Comment("系统权限表")
public class SysPermission extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -692457396908500198L;

	@Comment("权限编码")
	@Column(name = "code_")
	private String code;

	@Comment("权限名称")
	@Column(name = "name_")
	private String name;

	/// 排序号
	@Comment("排序号")
	@Column(name = "sort_number_")
	private int sortNum;

	@Comment("备注")
	@Column(name = "remark_")
	private String remark;

	@ManyToMany
	@JoinTable(name = "t_role_permission", joinColumns = {@JoinColumn(name = "permission_id_")}, inverseJoinColumns = {
			@JoinColumn(name = "role_id_")})
	private Set<SysRole> roles = Sets.newHashSet();
}
