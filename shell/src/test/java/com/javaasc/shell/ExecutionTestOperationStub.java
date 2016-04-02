package com.javaasc.shell;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.api.JascParameter;

public class ExecutionTestOperationStub {
    @JascOperation
    public String operation1(@JascParameter(name = "arg1") String arg1) throws Exception {
        return null;
    }

    @JascOperation
    public String operation2(
            @JascParameter(name = "arg1") Boolean arg1,
            @JascParameter(name = "arg2") Integer arg2) throws Exception {
        return arg1 + " " + arg2;
    }
}
