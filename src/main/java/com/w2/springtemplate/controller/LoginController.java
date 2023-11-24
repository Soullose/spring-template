package com.w2.springtemplate.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/w2")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity login(HttpServletRequest request){
//        UsernamePasswordToken token = new
//                UsernamePasswordToken("user", password);
//        Subject subject = SecurityUtils.getSubject();
//        subject.login(token);
//        return ResponseEntity.ok(commandBus.execute(InitSession.builder().request(request).build()));
        return ResponseEntity.ok().build();
    }
}
