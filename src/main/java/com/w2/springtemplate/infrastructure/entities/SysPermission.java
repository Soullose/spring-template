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
@Entity(name = "t_permission")
@Table(appliesTo = "t_permission", comment = "系统权限表")
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
}
