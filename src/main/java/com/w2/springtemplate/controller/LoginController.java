package com.w2.springtemplate.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public ResponseEntity login(@RequestParam String password){
        UsernamePasswordToken token = new
                UsernamePasswordToken("user", password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return ResponseEntity.ok().build();
    }
}
