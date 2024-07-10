package com.w2.springtemplate.infrastructure.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysPermission implements Serializable {

	private String code;

	private String name;

	/// 排序号
	private int sortIndex;

	private String remark;
}
