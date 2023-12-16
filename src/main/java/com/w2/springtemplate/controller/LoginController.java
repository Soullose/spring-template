package com.w2.springtemplate.controller;

import com.w2.springtemplate.framework.command.handler.RunEnvironment;
import com.w2.springtemplate.framework.handlers.login.LoggedInCommand;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "登录")
@Slf4j
@RestController
@RequestMapping("api/login")
public class LoginController {

	@Autowired
	private RunEnvironment runEnvironment;

	@PostMapping()
	public ResponseEntity<Object> login(HttpServletRequest request) {

//		return ResponseEntity.ok(LoggedInUser.fromMap((Map<String, Object>) SecurityUtils.getSubject().getPrincipal()));
//		return ResponseEntity.ok((LoggedInUser) SecurityUtils.getSubject().getPrincipal());
		return ResponseEntity.ok(runEnvironment.run(new LoggedInCommand()));
	}
}
