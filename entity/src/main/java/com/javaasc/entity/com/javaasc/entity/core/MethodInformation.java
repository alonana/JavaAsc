package com.javaasc.entity.com.javaasc.entity.core;

import com.javaasc.entity.JascOperation;
import com.javaasc.util.JascException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.LinkedList;

public class MethodInformation {
    private Method method;
    private String name;
    private String description;
    private HashMap<String, OptionInformation> options;
    private LinkedList<OptionInformation> optionsSorted;

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
        options = new HashMap<>();
        optionsSorted = new LinkedList<>();
        for (Parameter parameter : method.getParameters()) {
            OptionInformation information = new OptionInformation(parameter, method);
            optionsSorted.add(information);
            for (String name : information.getNames()) {
                OptionInformation existing = options.get(name);
                if (existing != null) {
                    throw new JascException("duplicate options using the same name '" + name
                            + "' located in class " + method.getDeclaringClass().getName());
                }
                options.put(name, information);
            }
        }
    }

    public Method getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public String execute(Arguments arguments) throws Exception {
        arguments.usageReset();
        Object[] parameters = new Object[optionsSorted.size()];
        int i = 0;
        for (OptionInformation information : optionsSorted) {
            String value = information.getValue(arguments);
            parameters[i++] = value;
        }
        arguments.usageVerify();
        Object o = method.getDeclaringClass().newInstance();
        Object result = method.invoke(o, parameters);
        return result.toString();
    }
}
