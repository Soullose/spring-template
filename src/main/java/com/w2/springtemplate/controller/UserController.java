package com.w2.springtemplate.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.w2.springtemplate.command.user.ChangePasswordCommand;
import com.w2.springtemplate.command.user.CheckPasswordCommand;
import com.w2.springtemplate.command.user.UpdateSysUserCommand;
import com.w2.springtemplate.domain.model.user.User;
import com.w2.springtemplate.domain.service.user.SysUserService;
import com.w2.springtemplate.framework.command.handler.RunEnvironment;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.interfaces.user.facade.converters.SysUserDTOConverter;
import com.w2.springtemplate.interfaces.user.facade.dto.SysUserDTO;
import com.w2.springtemplate.model.params.RegisterSysUserParams;
import com.w2.springtemplate.model.params.UpdateSysUserParams;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "用户管理")
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

	private final SysUserService sysUserService;
	private final RunEnvironment runEnvironment;

	public UserController(SysUserService sysUserService, RunEnvironment runEnvironment) {
		this.sysUserService = sysUserService;
		this.runEnvironment = runEnvironment;
	}

	@Operation(summary = "注册用户")
	@PostMapping("/register")
	public ResponseEntity<SysUserDTO> userRegister(@RequestBody RegisterSysUserParams params) {
		SysUser register = sysUserService.register(params);
		return ResponseEntity.ok(SysUserDTOConverter.INSTANCE.fromPO(register));
		// return
		// ResponseEntity.ok(runEnvironment.run(RegisterSysUserCommand.builder().params(params).build()));
	}

	@Operation(summary = "校验密码")
	@GetMapping("/check")
	public ResponseEntity<SysUserDTO> check(@RequestParam String username, @RequestParam String password) {
		return ResponseEntity
				.ok(runEnvironment.run(CheckPasswordCommand.builder().username(username).password(password).build()));
	}

	@Operation(summary = "更新用户")
	@PostMapping("/update")
	public ResponseEntity<SysUserDTO> userUpdate(@RequestBody UpdateSysUserParams params) {
		return ResponseEntity.ok(runEnvironment.run(UpdateSysUserCommand.builder().params(params).build()));
	}

	@Operation(summary = "获取所有用户")
	@GetMapping("/findAllUser")
	public ResponseEntity<List<User>> findAllUser() {
		return ResponseEntity.ok(sysUserService.findAllUser());
	}

	@Operation(summary = "重置用户密码")
	@Parameters(value = {@Parameter(name = "id", description = "用户编号", required = true)})
	@GetMapping("/resetPassword")
	public ResponseEntity<SysUserDTO> resetPassword(@RequestParam String id) {
		return ResponseEntity.ok(SysUserDTOConverter.INSTANCE.fromPO(sysUserService.resetPassword(id)));
	}

	@Operation(summary = "修改用户密码")
	@Parameters(value = {
			@Parameter(name = "id", description = "用户编号", required = true, schema = @Schema(type = "string")),
			@Parameter(name = "password", description = "用户密码", required = true, schema = @Schema(type = "string", format = "password"))})
	@GetMapping("/changeUserPassword")
	public ResponseEntity<SysUserDTO> changeUserPassword(@RequestParam String id, @RequestParam String password) {
		return ResponseEntity.ok(runEnvironment.run(ChangePasswordCommand.builder().id(id).password(password).build()));
	}

}
