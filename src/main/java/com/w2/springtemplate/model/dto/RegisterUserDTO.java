package com.w2.springtemplate.model.dto;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private String id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String idCard;
}
