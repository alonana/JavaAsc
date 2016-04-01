package com.javaasc.entity.core;

import com.javaasc.entity.api.JascOption;
import com.javaasc.entity.api.JascValues;
import com.javaasc.util.JascException;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ParameterInformation {
    public static final String MISSING_VALUE_FOR_PARAMETER = "missing value for parameter ";
    private final Class<? extends JascValues> values;
    private final boolean autoSort;
    private final MethodInformation method;
    private String name;

    public ParameterInformation(Parameter parameter, MethodInformation method) {
        this.method = method;
        JascOption annotation = parameter.getAnnotation(JascOption.class);
        if (annotation == null) {
            throw new JascException("missing annotation " + JascOption.class.getName() + " for parameter in " +
                    method.getFullName());
        }
        name = annotation.name();
        values = annotation.values();
        autoSort = annotation.automaticSortValue();
    }

    public String getName(boolean withDash) {
        if (withDash) {
            return "-" + name;
        }
        return name;
    }

    public Object getValue(Arguments arguments) {
        Object value = arguments.getArgumentValue(name);
        if (value == null) {
            throw new JascException(MISSING_VALUE_FOR_PARAMETER + name + " in " + method.getFullName());
        }
        arguments.usageMark(name);
        return value;
    }

    public List<String> getValues() throws Exception {
        LinkedList<String> result = new LinkedList<>();
        JascValues jascValues = values.newInstance();
        result.addAll(jascValues.getValues());
        if (autoSort) {
            Collections.sort(result);
        }
        return result;
    }
}
