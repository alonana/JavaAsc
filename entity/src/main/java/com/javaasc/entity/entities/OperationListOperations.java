package com.javaasc.entity.entities;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.core.JascEntities;
import com.javaasc.util.CollectionUtil;

public class OperationListOperations {
    @SuppressWarnings("unused")
    @JascOperation(shortDescription = "list all available commands")
    public String listOperations() {
        return CollectionUtil.getNiceList(JascEntities.INSTANCE.getOperationsNames());
    }
}
