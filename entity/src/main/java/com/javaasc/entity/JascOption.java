package com.javaasc.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface JascOption {
    String[] names();

    String description() default EMPTY;

    static String EMPTY = "overcome java non support for null default";
}
