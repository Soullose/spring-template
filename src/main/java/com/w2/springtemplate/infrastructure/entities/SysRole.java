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
@Table(name = "t_sys_role")
@Entity
public class SysRole extends BaseEntity implements Serializable {

	@Column(name = "role_code_")
	private String roleCode;
	@Column(name = "role_name_")
	private String roleName;
}
