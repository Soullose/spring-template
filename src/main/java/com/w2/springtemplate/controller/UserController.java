package com.w2.springtemplate.controller;

import com.w2.springtemplate.command.user.CheckPasswordCommand;
import com.w2.springtemplate.command.user.RegisterSysUserCommand;
import com.w2.springtemplate.domain.model.user.SysUserService;
import com.w2.springtemplate.domain.model.user.User;
import com.w2.springtemplate.framework.command.handler.RunEnvironment;
import com.w2.springtemplate.infrastructure.repository.SysUserRepository;
import com.w2.springtemplate.interfaces.user.facade.dto.SysUserDTO;
import com.w2.springtemplate.model.params.RegisterSysUserParams;
import com.w2.springtemplate.utils.crypto.PasswordEncoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

	private final PasswordEncoder passwordEncoder;
	private final SysUserRepository sysUserRepository;
	private final SysUserService sysUserService;
	private final RunEnvironment runEnvironment;

	public UserController(PasswordEncoder passwordEncoder, SysUserRepository sysUserRepository,SysUserService sysUserService,
						  RunEnvironment runEnvironment) {
		this.passwordEncoder = passwordEncoder;
		this.sysUserRepository = sysUserRepository;
		this.sysUserService = sysUserService;
		this.runEnvironment = runEnvironment;
	}

	@ApiOperation(value = "注册")
	@PostMapping("/register")
	public ResponseEntity<SysUserDTO> userRegister(@RequestBody RegisterSysUserParams params) {
		return ResponseEntity.ok(runEnvironment.run(RegisterSysUserCommand.builder().params(params).build()));
	}

	@ApiOperation(value = "校验密码")
	@GetMapping("/check")
	public ResponseEntity<SysUserDTO> check(@RequestParam String username, @RequestParam String password) {
		return ResponseEntity.ok(runEnvironment.run(CheckPasswordCommand.builder().username(username).password(password).build()));
	}

	@ApiOperation(value = "获取所有用户")
	@GetMapping("/findAllUser")
	public ResponseEntity<List<User>> findAllUser(){
		return ResponseEntity.ok(sysUserService.findAllUser());
	}

}
