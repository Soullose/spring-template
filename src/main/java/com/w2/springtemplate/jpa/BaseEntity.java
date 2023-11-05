package com.w2.springtemplate.jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "com.w2.springtemplate.jpa.UUIDGenerator")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id_")
    @Access(AccessType.PROPERTY)
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public boolean equals(Object object) {
//        if (this == object)
//            return true;
//
//        if (object == null)
//            return false;
//        if (object == null || getClass() != object.getClass())
//            return false;
//
//        final BaseEntity other = (BaseEntity) object;
//
//        return id.equals(other.id);
//    }

//    public int hashCode() {
//        return (id == null) ? super.hashCode() : id.hashCode();
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
