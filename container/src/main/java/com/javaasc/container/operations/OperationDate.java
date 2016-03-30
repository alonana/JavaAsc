package com.javaasc.container.operations;

import com.javaasc.entity.JascOperation;
import com.javaasc.util.TimeUtil;

public class OperationDate {
    @SuppressWarnings("unused")
    @JascOperation
    public String date() throws Exception {
        return TimeUtil.getTimeCurrent(false);
    }
}
