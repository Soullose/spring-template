package com.w2.springtemplate.model;

import com.w2.springtemplate.framework.jpa.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "t_sys_role")
@Entity
public class SysRole extends BaseEntity implements Serializable {

    private String roleCode;
    private String roleName;
}
