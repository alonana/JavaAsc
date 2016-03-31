package com.javaasc.entity.com.javaasc.entity.core;

import com.javaasc.entity.api.JascOption;
import com.javaasc.entity.api.JascValues;
import com.javaasc.util.JascException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class OptionInformation {
    private final Class<? extends JascValues> values;
    private final boolean autoSort;
    private String name;

    public OptionInformation(Parameter parameter, Method method) {
        JascOption annotation = parameter.getAnnotation(JascOption.class);
        name = "-" + annotation.name();
        values = annotation.values();
        autoSort = annotation.automaticSortValue();
    }

    public String getName() {
        return name;
    }

    public String getValue(Arguments arguments) {
        String value = arguments.getArgumentSimple(name);
        if (value != null) {
            arguments.usageMark(name);
            return value;
        }
        throw new JascException("missing value for option " + name);
    }

    public List<String> getValues() throws Exception {
        LinkedList result = new LinkedList();
        JascValues jascValues = values.newInstance();
        result.addAll(jascValues.getValues());
        if (autoSort) {
            Collections.sort(result);
        }
        return result;
    }
}
