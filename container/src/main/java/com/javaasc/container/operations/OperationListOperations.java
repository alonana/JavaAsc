package com.javaasc.container.operations;

import com.javaasc.entity.JascOperation;
import com.javaasc.entity.com.javaasc.entity.core.ClassAnalyzer;
import com.javaasc.util.CollectionUtil;

public class OperationListOperations {
    @SuppressWarnings("unused")
    @JascOperation(shortDescription = "list all available commands")
    public String listOperations() {
        return CollectionUtil.getNiceList(ClassAnalyzer.INSTANCE.getOperationsNames());
    }
}
