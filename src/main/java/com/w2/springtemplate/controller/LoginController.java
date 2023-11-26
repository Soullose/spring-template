package com.w2.springtemplate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2.springtemplate.framework.shiro.model.LoggedInUser;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "登录")
@Slf4j
@RestController
@RequestMapping("api/login")
public class LoginController {

	@PostMapping()
	public ResponseEntity login(HttpServletRequest request) {
		log.info("login:{}", request.getHeader("Authorization"));
		ObjectMapper objectMapper = new ObjectMapper();

		log.info("login:{}", LoggedInUser.fromMap((Map) SecurityUtils.getSubject().getPrincipal()));
		// objectMapper.r
		// UsernamePasswordToken token = new
		// UsernamePasswordToken("user", password);
		// Subject subject = SecurityUtils.getSubject();
		// subject.login(token);
		// return
		// ResponseEntity.ok(commandBus.execute(InitSession.builder().request(request).build()));

		return ResponseEntity.ok(LoggedInUser.fromMap((Map<String, Object>) SecurityUtils.getSubject().getPrincipal()));
	}
}
