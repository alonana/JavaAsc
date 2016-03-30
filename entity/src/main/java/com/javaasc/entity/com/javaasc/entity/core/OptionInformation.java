package com.javaasc.entity.com.javaasc.entity.core;

import com.javaasc.entity.JascOption;
import com.javaasc.util.JascException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class OptionInformation {
    private String names[];

    public OptionInformation(Parameter parameter, Method method) {
        JascOption annotation = parameter.getAnnotation(JascOption.class);
        names = new String[annotation.names().length];
        for (int i = 0; i < annotation.names().length; i++) {
            names[i] = "-" + annotation.names()[i];
        }
        if (names.length == 0) {
            throw new JascException("names must be specified for " + parameter.getName() + " in " +
                    method.getDeclaringClass().getName() + "." + method.getName());
        }
    }

    public String[] getNames() {
        return names;
    }

    public String getValue(Arguments arguments) {
        for (String name : names) {
            String value = arguments.getArgumentSimple(name);
            if (value != null) {
                arguments.usageMark(name);
                return value;
            }
        }
        throw new JascException("missing value for option " + names[0]);
    }
}
