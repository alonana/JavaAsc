package com.javaasc.shell;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.api.JascParameter;

public class CompletionTestOperationStub {
    @JascOperation
    public String operation1() throws Exception {
        return null;
    }

    @JascOperation(name = "operation3")
    public String operation2() throws Exception {
        return null;
    }

    @JascOperation
    public String another() throws Exception {
        return null;
    }

    @JascOperation
    public String checkArgs1(@JascParameter(name = "argument1") String argument1) throws Exception {
        return null;
    }

    @JascOperation
    public String checkArgs2(@JascParameter(name = "a1a") String x,
                             @JascParameter(name = "a2a") String y,
                             @JascParameter(name = "bbb") String z) throws Exception {
        return null;
    }

    @JascOperation
    public String checkValues(@JascParameter(name = "a1", values = ValuesStub.class) String x) throws Exception {
        return null;
    }
}
