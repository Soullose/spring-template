package com.w2.springtemplate.infrastructure.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysMenu implements Serializable {

	private static final long serialVersionUID = 8521345807755801047L;

	private String Code;

	private String name;

	private String path;

	private String remark;
}
