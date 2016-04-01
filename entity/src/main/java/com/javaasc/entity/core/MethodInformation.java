package com.javaasc.entity.core;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.util.JascException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class MethodInformation {
    public static final String ERROR_RUNNING = "error running ";

    private Method method;
    private String name;
    private String description;
    private HashMap<String, ParameterInformation> parameters;
    private LinkedList<ParameterInformation> parametersSorted;

    public MethodInformation(Method method) {
        this.method = method;
        JascOperation annotation = method.getAnnotation(JascOperation.class);
        name = annotation.name();
        if (name.equals(JascOperation.EMPTY)) {
            name = method.getName();
        }
        description = annotation.shortDescription();
        if (description.equals(JascOperation.EMPTY)) {
            description = null;
        }
        parameters = new HashMap<>();
        parametersSorted = new LinkedList<>();
        for (Parameter methodParameter : method.getParameters()) {
            ParameterInformation parameter = new ParameterInformation(methodParameter, this);
            parametersSorted.add(parameter);
            ParameterInformation existing = parameters.get(parameter.getName(false));
            if (existing != null) {
                throw new JascException("duplicate options using the same name '" + parameter.getName(false)
                        + "' located in " + getFullName());
            }
            parameters.put(parameter.getName(false), parameter);
        }
    }

    public Method getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return method.getDeclaringClass().getName() + "." + method.getName() + "()";
    }

    public String execute(Arguments arguments) throws Exception {
        arguments.usageReset();
        Object[] parameters = new Object[parametersSorted.size()];
        int i = 0;
        for (ParameterInformation information : parametersSorted) {
            Object value = information.getValue(arguments);
            parameters[i++] = value;
        }
        arguments.usageVerify();
        try {
            Object o = method.getDeclaringClass().newInstance();
            Object result = method.invoke(o, parameters);
            if (result == null) {
                return null;
            }
            return result.toString();
        } catch (Throwable e) {
            throw new JascException(ERROR_RUNNING + getFullName(), e);
        }
    }

    public Collection<String> getParametersNames(boolean withDash) {
        if (withDash) {
            return parameters.keySet().stream().map(e -> "-" + e).collect(Collectors.toList());
        }
        return new HashSet<>(parameters.keySet());
    }

    public ParameterInformation getParameter(String name, boolean withDash) {
        if (withDash) {
            if (name.isEmpty()) {
                return null;
            }
            name = name.substring(1);
        }
        return parameters.get(name);
    }
}
