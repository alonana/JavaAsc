package com.javaasc.entity.core;

import com.javaasc.entity.api.JascParameter;
import com.javaasc.entity.api.JascValues;
import com.javaasc.util.JascException;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ParameterInformation {
    public static final String MISSING_VALUE_FOR_PARAMETER = "missing value for parameter ";
    public static final String NOT_SUPPORTED_TYPE = " is not a supported parameter type";
    public static final String WRONG_CLASS = "wrong class type for parameter ";

    private static final Class[] SUPPORTED_CLASSES = {String.class, Integer.class, Boolean.class};
    private final Class<? extends JascValues> values;
    private final boolean autoSort;
    private final Class<?> parameterType;
    private final MethodInformation method;
    private String name;

    public ParameterInformation(Parameter parameter, Class<?> parameterType, MethodInformation method) {
        this.parameterType = parameterType;
        this.method = method;
        JascParameter annotation = parameter.getAnnotation(JascParameter.class);
        if (annotation == null) {
            throw new JascException("missing annotation " + JascParameter.class.getName() + " for parameter in " +
                    method.getFullName());
        }
        name = annotation.name();
        values = annotation.values();
        autoSort = annotation.automaticSortValue();

        boolean supportedClass = Arrays.stream(SUPPORTED_CLASSES).anyMatch(aClass -> aClass.equals(parameterType));
        if (!supportedClass) {
            throw new JascException("parameter " + getName(false) + " of type " + parameterType.getName() +
                    " in " + method.getFullName() + NOT_SUPPORTED_TYPE);
        }
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

        if (value.getClass() != this.parameterType) {
            if (value.getClass().equals(String.class)) {
                value = convertValueFromString(value);
            } else {
                throw new JascException(WRONG_CLASS + getName(false) +
                        " in " + method.getFullName());
            }
        }
        arguments.usageMark(name);
        return value;
    }

    private Object convertValueFromString(Object value) {
        String s = (String) value;
        if (parameterType == Integer.class) {
            return Integer.valueOf(s);
        }
        if (parameterType == Boolean.class) {
            return Boolean.valueOf(s);
        }
        throw new JascException("unable to convert parameter " + getName(false) + " of type " +
                parameterType.getName() + " in " + method.getFullName() + NOT_SUPPORTED_TYPE);
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
