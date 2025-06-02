package com.w2.springtemplate.infrastructure.entities;

import java.io.Serializable;

import org.hibernate.annotations.Comment;

import com.w2.springtemplate.framework.jpa.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "t_sys_user_group")
@Table(name = "t_sys_user_group")
@Comment("系统用户组表")
public class SysUserGroup extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 7655253369587049270L;

	@Comment("编号")
	@Column(name = "code_")
	private String code;

	@Comment("名称")
	@Column(name = "name_")
	private String name;

	@Comment("备注")
	@Column(name = "remark_")
	private String remark;
}
