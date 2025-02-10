package com.w2.springtemplate.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.w2.springtemplate.framework.jpa.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_sys_user")
@Entity
public class SysUser extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 4220958672909825116L;
	/// 登录账户,唯一.
	@Column(name = "user_name_", unique = true)
	private String username;

	/// 密码
	@Column(name = "password_")
	private String password;

	/// 姓名
	@Column(name = "name_")
	private String name;

	/// 身份证
	@Column(name = "id_card_")
	private String idCard;

	/// 证件类型
	@Column(name = "id_card_type_")
	private String idCardType;

	/// 邮箱
	@Column(name = "email_")
	private String email;

	/// 电话
	@Column(name = "phone_")
	private String phone;

	/// 籍贯
	@Column(name = "native_place_")
	private String nativePlace;

	/// 生日
	@Column(name = "birthday_")
	private LocalDate birthday;

	/// 关联角色多对多
    @JsonIgnore
	@ManyToMany
	@JoinTable(name = "t_user_role", joinColumns = {@JoinColumn(name = "user_id_")}, inverseJoinColumns = {
			@JoinColumn(name = "role_id_")})
	private Set<SysRole> roles = Sets.newHashSet();

	/// 关联用户组多对一
    @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_group_id_")
	@Comment("用户组id")
	private SysUserGroup sysUserGroup;

    @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "tenant_id_")
	private SysTenant tenant;
}
