package com.w2.springtemplate.infrastructure.entities;

import com.w2.springtemplate.framework.jpa.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "t_sys_org")
@Table(appliesTo = "t_sys_org", comment = "系统组织表")
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
