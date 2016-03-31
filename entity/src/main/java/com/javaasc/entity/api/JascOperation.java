package com.javaasc.entity.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JascOperation {
    static String EMPTY = "overcome java non support for null default";

    String name() default EMPTY;

    String shortDescription() default EMPTY;
}
