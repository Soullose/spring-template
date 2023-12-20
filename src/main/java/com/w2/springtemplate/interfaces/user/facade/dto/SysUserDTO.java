package com.w2.springtemplate.interfaces.user.facade.dto;

import lombok.Data;

@Data
public class SysUserDTO {
	private String id;
	private String name;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String idCard;
}
