package com.w2.springtemplate.interfaces.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "系统用户管理")
@RestController
@RequestMapping("sys-user")
@Slf4j
public class SysUserController {

	@Operation(summary = "注册1")
	@PostMapping("/register")
	public void test() {
	}
}
