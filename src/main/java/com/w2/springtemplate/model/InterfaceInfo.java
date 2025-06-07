package com.w2.springtemplate.model;

import java.io.Serializable;

import com.w2.springtemplate.framework.jpa.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_interface_info")
public class InterfaceInfo extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "name_")
	private String name;
	@Column(name = "url_")
	private String url;
	@Column(name = "method_")
	private String method;
	@Column(name = "description_", columnDefinition = "varchar(500) COMMENT '接口描述'")
	private String description;

	@Column(name = "request_header_")
	private String requestHeader;
	@Column(name = "response_header_")
	private String responseHeader;

	@Column(name = "status_")
	private boolean status;

	@Column(name = "delete_")
	private boolean delete;
}
