package com.w2.springtemplate.jpa;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    @Id
    @GenericGenerator(name = "NanoidGenerator", strategy = "com.w2.springtemplate.jpa.NanoidGenerator")
    @GeneratedValue(generator = "NanoidGenerator")
    @Column(name = "id_")
    @Access(AccessType.PROPERTY)
    private String id;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
