package com.javaasc.entity.entities;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.api.JascOption;

public class OperationEcho {
    @SuppressWarnings("unused")
    @JascOperation()
    public String echo(@JascOption(name = "message") String message) throws Exception {
        return message;
    }
}
