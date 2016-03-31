package com.javac.shell;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.api.JascOption;

public class OperationStub {
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
    public String checkArgs1(@JascOption(name = "argument1") String argument1) throws Exception {
        return null;
    }

    @JascOperation
    public String checkArgs2(@JascOption(name = "a1a") String x,
                             @JascOption(name = "a2a") String y,
                             @JascOption(name = "bbb") String z) throws Exception {
        return null;
    }

    @JascOperation
    public String checkValues(@JascOption(name = "a1", values = ValuesStub.class) String x) throws Exception {
        return null;
    }
}
