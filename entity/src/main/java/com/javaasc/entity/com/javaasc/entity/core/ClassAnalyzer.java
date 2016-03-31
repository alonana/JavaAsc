package com.javaasc.entity.com.javaasc.entity.core;

import com.javaasc.entity.api.JascOperation;
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

    private HashMap<String, MethodInformation> operations = new HashMap<>();

    public List<String> getOperationsNames() {
        List<String> names = new LinkedList<String>();
        names.addAll(operations.keySet());
        Collections.sort(names);
        return names;
    }

    public void clear() {
        operations.clear();
    }

    public void analyzeClass(Class checkedClass) {
        for (Method method : checkedClass.getMethods()) {
            if (method.isAnnotationPresent(JascOperation.class)) {
                addMethod(method);
            }
        }
    }

    private void addMethod(Method method) {
        MethodInformation information = new MethodInformation(method);
        MethodInformation existing = operations.get(information.getName());
        if (existing != null) {
            throw new JascException("duplicate methods using the same name '" + information.getName()
                    + "' located: " + existing.getMethod().getDeclaringClass().getName() + ", " +
                    method.getDeclaringClass().getName());
        }
        operations.put(information.getName(), information);
        logger.debug("operation {} added for {}.{}",
                information.getName(), method.getClass().getName(), method.getName());
    }

    public String execute(Arguments arguments) throws Exception {
        String command = arguments.getCommand();
        MethodInformation methodInformation = operations.get(command);
        if (methodInformation == null) {
            throw new JascException("command not found: " + command);
        }
        return methodInformation.execute(arguments);
    }

    public MethodInformation getOperation(String operation) {
        return operations.get(operation);
    }
}
