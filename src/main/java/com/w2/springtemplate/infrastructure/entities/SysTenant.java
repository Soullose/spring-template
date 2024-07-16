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
@Entity(name = "t_sys_tenant")
@Table(appliesTo = "t_sys_tenant", comment = "租户表")
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
