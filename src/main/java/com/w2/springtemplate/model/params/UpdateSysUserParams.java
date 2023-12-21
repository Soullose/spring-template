package com.w2.springtemplate.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateSysUserParams {

    @ApiModelProperty(value = "id", required = true, example = "RbjjOBbGZ_JVAs5JhjqV8")
    private String id;
    @ApiModelProperty(value = "姓名", required = true, example = "王大锤")
    private String name;
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    private String username;
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;
    @ApiModelProperty(value = "邮箱", example = "xxx@xx.com")
    private String email;
    @ApiModelProperty(value = "电话", example = "110")
    private String phone;
    @ApiModelProperty(value = "地址", example = "XXX镇XXX路XXX号")
    private String address;
    @ApiModelProperty(value = "身份证", required = true, example = "3211XXXXXX1231")
    private String idCard;
}
