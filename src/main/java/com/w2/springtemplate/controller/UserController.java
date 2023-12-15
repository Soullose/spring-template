package com.w2.springtemplate.controller;

import com.w2.springtemplate.domain.model.user.SysUserService;
import com.w2.springtemplate.domain.model.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.querydsl.core.types.Predicate;
import com.w2.springtemplate.infrastructure.entities.QSysUser;
import com.w2.springtemplate.infrastructure.entities.SysUser;
import com.w2.springtemplate.infrastructure.repository.SysUserRepository;
import com.w2.springtemplate.model.dto.SysUserRegDTO;
import com.w2.springtemplate.utils.crypto.PasswordEncoder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

	private final PasswordEncoder passwordEncoder;
	private final SysUserRepository sysUserRepository;
	private final SysUserService sysUserService;

	public UserController(PasswordEncoder passwordEncoder, SysUserRepository sysUserRepository,SysUserService sysUserService) {
		this.passwordEncoder = passwordEncoder;
		this.sysUserRepository = sysUserRepository;
		this.sysUserService = sysUserService;
	}

	@ApiOperation(value = "注册")
	@PostMapping("/register")
	public ResponseEntity<SysUser> userRegister(@RequestBody SysUserRegDTO userRegDTO) {
		SysUser sysUser = new SysUser();
		sysUser.setName(userRegDTO.getName());
		sysUser.setUsername(userRegDTO.getUsername());
		sysUser.setPassword(passwordEncoder.encode(userRegDTO.getPassword()));
		// sysUser.setPassword(OpenBSDBCrypt.generate("123456".toCharArray(),new
		// byte[16],4));

		sysUserRepository.save(sysUser);
		return ResponseEntity.ok(sysUser);
	}

	@ApiOperation(value = "校验密码")
	@GetMapping("/check")
	public ResponseEntity<SysUser> check(@RequestParam String username, @RequestParam String password) {
		QSysUser qSysUser = QSysUser.sysUser;
		Predicate predicate = qSysUser.username.eq(username);
		SysUser sysUser = sysUserRepository.findOne(predicate).orElse(null);
		String password1 = sysUser.getPassword();
		boolean matches = passwordEncoder.matches(password, password1);
		// log.info("校验密码是否正确:{}",OpenBSDBCrypt.checkPassword(password1,password.toCharArray()));
		log.info("密码是否正确:{}", matches);
		return ResponseEntity.ok(sysUser);
	}

	@ApiOperation(value = "获取所有用户")
	@GetMapping("/findAllUser")
	public ResponseEntity<List<User>> findAllUser(){
		return ResponseEntity.ok(sysUserService.findAllUser());
	}

}
