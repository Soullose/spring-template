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
@Entity(name = "t_sys_org")
@Table(name = "t_sys_org")
@Comment("系统组织表")
public class SysOrg extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7836213317747597590L;

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
