package com.w2.springtemplate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("api/login")
public class LoginController {

	@PostMapping()
	public ResponseEntity login(HttpServletRequest request) {
		log.info("login:{}", request);
		// UsernamePasswordToken token = new
		// UsernamePasswordToken("user", password);
		// Subject subject = SecurityUtils.getSubject();
		// subject.login(token);
		// return
		// ResponseEntity.ok(commandBus.execute(InitSession.builder().request(request).build()));
		return ResponseEntity.ok().build();
	}
}
