package com.javaasc.entity.com.javaasc.entity.core;

import com.javaasc.entity.JascOperation;
import com.javaasc.util.JascException;
import com.javaasc.util.JascLogger;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public enum ClassAnalyzer {
    INSTANCE;

    private static final JascLogger logger = JascLogger.getLogger(ClassAnalyzer.class);

    private HashMap<String, Method> operations = new HashMap<String, Method>();

    public List<String> getOperationsNames() {
        List<String> names = new LinkedList<String>();
        names.addAll(operations.keySet());
        Collections.sort(names);
        return names;
    }

    public void analyzeClass(Class checkedClass) {
        for (Method method : checkedClass.getMethods()) {
            if (method.isAnnotationPresent(JascOperation.class)) {
                addMethod(method);
            }
        }
    }

    private void addMethod(Method method) {
        JascOperation annotation = method.getAnnotation(JascOperation.class);
        String name = annotation.name();
        if (name.equals(JascOperation.USE_METHOD_NAME)) {
            name = method.getName();
        }
        Method existing = operations.get(name);
        if (existing != null) {
            throw new JascException("duplicate methods using the same name '" + name
                    + "' located: " + existing.getClass().getName() + ", " + method.getClass().getName());
        }
        operations.put(name, method);
        logger.debug("operation {} added for {}.{}", name, method.getClass().getName(), method.getName());
    }
}
