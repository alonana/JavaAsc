package com.javac.shell;

import com.javaasc.entity.api.JascValues;

import java.util.LinkedList;
import java.util.List;

public class ValuesStub implements JascValues {
    @Override
    public List<String> getValues() {
        LinkedList<String> values = new LinkedList<>();
        values.add("value1111");
        values.add("value1122");
        return values;
    }
}
