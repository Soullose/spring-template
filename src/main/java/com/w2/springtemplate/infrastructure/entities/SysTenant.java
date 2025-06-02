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
@Entity(name = "t_sys_tenant")
@Table(name = "t_sys_tenant")
@Comment("租户表")
public class SysTenant extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 8499363370547484369L;

	@Comment("租户编码")
	@Column(name = "code_")
	private String code;

	@Comment("租户名称")
	@Column(name = "name_")
	private String name;

	@Comment("备注")
	@Column(name = "remark_")
	private String remark;
}
