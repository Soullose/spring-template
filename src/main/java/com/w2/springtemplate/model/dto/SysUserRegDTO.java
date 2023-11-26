package com.w2.springtemplate.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserRegDTO {
	@ApiModelProperty(value = "用户名", required = true, example = "admin")
	private String username;
	@ApiModelProperty(value = "密码", required = true, example = "123456")
	private String password;
	@ApiModelProperty(value = "姓名", required = true, example = "王大锤")
	private String name;
}
