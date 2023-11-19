package com.w2.springtemplate.controller;

import com.w2.springtemplate.command.PlatformCommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private PlatformCommandBus commandBus;

    @PostMapping
    public ResponseEntity login(HttpServletRequest request){
//        UsernamePasswordToken token = new
//                UsernamePasswordToken("user", password);
//        Subject subject = SecurityUtils.getSubject();
//        subject.login(token);
//        return ResponseEntity.ok(commandBus.execute(InitSession.builder().request(request).build()));
        return ResponseEntity.ok().build();
    }
}
