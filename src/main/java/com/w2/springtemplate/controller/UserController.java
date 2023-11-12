package com.w2.springtemplate.controller;

import com.querydsl.core.types.Predicate;
import com.w2.springtemplate.model.QSysUser;
import com.w2.springtemplate.model.SysUser;
import com.w2.springtemplate.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	private final SysUserRepository sysUserRepository;

	public UserController( SysUserRepository sysUserRepository) {
		this.sysUserRepository = sysUserRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<SysUser> userRegister(@RequestParam String username) {
		SysUser sysUser = new SysUser();
		sysUser.setName("王小明");
		sysUser.setUsername(username);
//		sysUser.setPassword(OpenBSDBCrypt.generate("123456".toCharArray(),new byte[16],4));

		sysUserRepository.save(sysUser);
		return ResponseEntity.ok(sysUser);
	}

	@GetMapping("/check")
	public ResponseEntity<SysUser> check(@RequestParam String username, @RequestParam String password) {
		QSysUser qSysUser = QSysUser.sysUser;
		Predicate predicate = qSysUser.username.eq(username);
		SysUser sysUser = sysUserRepository.findOne(predicate).orElse(null);
		String password1 = sysUser.getPassword();
//		log.info("校验密码是否正确:{}",OpenBSDBCrypt.checkPassword(password1,password.toCharArray()));
		return ResponseEntity.ok(sysUser);
	}

}
