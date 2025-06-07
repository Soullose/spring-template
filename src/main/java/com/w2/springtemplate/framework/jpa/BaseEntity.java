package com.w2.springtemplate.framework.jpa;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Data;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

	@Id
	@BaseId
	@GeneratedValue(generator = "IdGenerator")
	@Column(name = "id_")
	@Access(AccessType.PROPERTY)
	private String id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@CreatedDate
	@Column(name = "created_at_")
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(name = "create_user_id_")
	private String createUserId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@LastModifiedDate
	@Column(name = "updated_at_")
	private LocalDateTime updatedAt;

	@LastModifiedBy
	@Column(name = "update_user_id_")
	private String updateUserId;

	// @Column(name = "create_user_name_")
	// private String createUserName;
	//
	// @PrePersist
	// public void currentLoggedInName(){
	// createUserName = new CurrentLoggedInName().getName();
	// }

}
