package com.w2.springtemplate.model.params;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateSysUserParams {

    @Schema(name = "id", required = true, example = "RbjjOBbGZ_JVAs5JhjqV8")
    private String id;
    @Schema(name = "姓名", required = true, example = "王大锤")
    private String name;
    @Schema(name = "用户名", required = true, example = "admin")
    private String username;
    @Schema(name = "密码", required = true, example = "123456")
    private String password;
    @Schema(name = "邮箱", example = "xxx@xx.com")
    private String email;
    @Schema(name = "电话", example = "110")
    private String phone;
    @Schema(name = "地址", example = "XXX镇XXX路XXX号")
    private String address;
    @Schema(name = "身份证", required = true, example = "3211XXXXXX1231")
    private String idCard;
}
