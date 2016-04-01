package com.javaasc.entity;

import com.javaasc.entity.api.JascOperation;

public class EntityWithoutParemeterAnnotation {
    @JascOperation()
    public void methodWithArgumentString(String input1) throws Exception {
    }
}
