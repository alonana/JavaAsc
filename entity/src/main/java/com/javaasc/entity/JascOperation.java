package com.javaasc.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JascOperation {
    String name() default EMPTY;
    String shortDescription() default EMPTY;

    static String EMPTY = "overcome java non support for null default";
}
