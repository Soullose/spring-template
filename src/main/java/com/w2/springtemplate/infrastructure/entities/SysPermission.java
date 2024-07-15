package com.w2.springtemplate.infrastructure.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysPermission implements Serializable {

	private static final long serialVersionUID = -692457396908500198L;

	private String code;

	private String name;

	/// 排序号
	private int sortNum;

	private String remark;
}
