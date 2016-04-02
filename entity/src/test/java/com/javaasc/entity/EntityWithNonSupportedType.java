package com.javaasc.entity;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.api.JascParameter;

public class EntityWithNonSupportedType {
    @JascOperation()
    public void m(@JascParameter(name = "a") Class input1) throws Exception {
    }
}
