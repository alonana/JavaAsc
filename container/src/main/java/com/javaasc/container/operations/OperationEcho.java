package com.javaasc.container.operations;

import com.javaasc.entity.JascOperation;
import com.javaasc.entity.JascOption;

public class OperationEcho {
    @SuppressWarnings("unused")
    @JascOperation()
    public String echo(@JascOption(names = {"message"}) String message) throws Exception {
        return message;
    }
}
