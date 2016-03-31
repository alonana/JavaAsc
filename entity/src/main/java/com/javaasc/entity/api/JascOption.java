package com.javaasc.entity.api;

import com.javaasc.entity.core.EmptyValues;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface JascOption {
    static String EMPTY = "overcome java non support for null default";

    String name();

    String description() default EMPTY;

    Class<? extends JascValues> values() default EmptyValues.class;

    boolean validateValues() default true;

    boolean automaticSortValue() default true;
}
