package com.w2.springtemplate.model;

import com.w2.springtemplate.framework.jpa.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "t_sys_user")
@Entity
public class SysUser extends BaseEntity implements Serializable {
    /// 登录账户,唯一.
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
}
