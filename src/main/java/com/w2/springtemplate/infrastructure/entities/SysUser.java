package com.w2.springtemplate.infrastructure.entities;

import com.w2.springtemplate.framework.jpa.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "t_sys_user")
@Entity
public class SysUser extends BaseEntity implements Serializable {
	/// 登录账户,唯一.
	@Column(name = "user_name_", unique = true)
	private String username;

	/// 密码
	@Column(name = "password_")
	private String password;

	///姓名
	@Column(name = "name_")
	private String name;

	/// 身份证
	@Column(name = "id_card_")
	private String idCard;
}
