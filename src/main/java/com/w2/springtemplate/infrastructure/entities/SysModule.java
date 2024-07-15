package com.w2.springtemplate.infrastructure.entities;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wsfzj 2024/7/15
 * @description 菜单分组
 * @version 1.0
 */
@Data
public class SysModule implements Serializable {

	private static final long serialVersionUID = -7426334849062799309L;
	/** 编号 */
	private String code;

	/** 名称 */
	private String name;

	/** 路径 */
	private String path;

	/** 排序号 */
	private int sortNum;
}
