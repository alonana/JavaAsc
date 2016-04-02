package com.javaasc.entity.entities;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.api.JascParameter;

public class OperationEcho {
    @SuppressWarnings("unused")
    @JascOperation()
    public String echo(@JascParameter(name = "message") String message) throws Exception {
        return message;
    }
}
