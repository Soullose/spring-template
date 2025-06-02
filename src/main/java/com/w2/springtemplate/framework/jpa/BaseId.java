package com.w2.springtemplate.framework.jpa;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.annotations.IdGeneratorType;



@Target({FIELD})
@Retention(RUNTIME)
@IdGeneratorType(IdGenerator.class)
public @interface BaseId {
}
