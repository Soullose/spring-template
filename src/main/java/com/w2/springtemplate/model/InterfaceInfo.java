package com.w2.springtemplate.model;

import com.w2.springtemplate.framework.jpa.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "t_interface_info")
@Entity
public class InterfaceInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String url;
    private String method;
    private String description;
}
