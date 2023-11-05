package com.w2.springtemplate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @PostMapping("/register")
    public ResponseEntity userRegister() {
        return ResponseEntity.ok().build();
    }


}
