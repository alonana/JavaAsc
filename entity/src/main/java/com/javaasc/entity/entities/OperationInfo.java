package com.javaasc.entity.entities;

import com.javaasc.entity.api.JascOperation;

public class OperationInfo {
    @SuppressWarnings("unused")
    @JascOperation
    public String info() throws Exception {
        return "This is a JASC container";
    }
}
