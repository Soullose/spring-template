package com.w2.springtemplate.infrastructure.entities;

import lombok.Data;

import java.io.Serializable;
@Data
public class SysMenu implements Serializable {

	private String Code;

	private String name;

	private String path;

	private String remark;
}
