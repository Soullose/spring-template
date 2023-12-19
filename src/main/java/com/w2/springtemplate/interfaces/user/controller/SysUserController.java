package com.w2.springtemplate.interfaces.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "系统用户管理")
@RestController
@RequestMapping("user")
@Slf4j
public class SysUserController {
}
