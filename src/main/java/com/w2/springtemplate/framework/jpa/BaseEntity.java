package com.w2.springtemplate.framework.jpa;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    @Id
    @GenericGenerator(name = "NanoidGenerator", strategy = "com.w2.springtemplate.framework.jpa.NanoidGenerator")
    @GeneratedValue(generator = "NanoidGenerator")
    @Column(name = "id_")
    @Access(AccessType.PROPERTY)
    private String id;



    @CreatedDate
    @Column(name = "created_at_")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "create_user_id_")
    private String createUserId;

    @LastModifiedDate
    @Column(name = "updated_at_")
    private LocalDateTime updatedAt;


    @Column(name = "create_user_name_")
    private String createUserName;

    @PrePersist
    public void currentLoggedInName(){
        createUserName = new CurrentLoggedInName().getName();
    }

}
