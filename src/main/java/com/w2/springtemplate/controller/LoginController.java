package com.w2.springtemplate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.w2.springtemplate.framework.command.CommandHandlerProvider;
import com.w2.springtemplate.framework.handlers.InitTest;
import com.w2.springtemplate.framework.handlers.InitTestHandler;
import com.w2.springtemplate.framework.handlers.Test;
import com.w2.springtemplate.framework.shiro.model.LoggedInUser;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = "登录")
@Slf4j
@RestController
@RequestMapping("api/login")
public class LoginController {


	@Autowired
	private CommandHandlerProvider commandHandlerProvider;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping()
	public ResponseEntity<Object> login(HttpServletRequest request) {
//		redisTemplate.opsForValue().set("a","b");

		InitTest initTest = new InitTest();
		initTest.setRequest(request);

		Test test = commandHandlerProvider.sendCommand(initTest);

		return ResponseEntity.ok(LoggedInUser.fromMap((Map<String, Object>) SecurityUtils.getSubject().getPrincipal()));
	}
}
