package com.javaasc.entity.core;

import com.javaasc.util.JascException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Arguments {
    public static final String INVALID_ARGUMENTS = "invalid arguments: ";
    public static final String ALREADY_SPECIFIED = " already specified";

    private String command;
    private HashMap<String, Object> arguments;
    private HashSet<String> notUsedArguments;

    public Arguments(String command) throws Exception {
        arguments = new HashMap<>();
        this.command = command;
    }

    public Arguments(List<String> rawArgs) throws Exception {
        arguments = new HashMap<>();
        if (rawArgs.isEmpty()) {
            throw new JascException("empty arguments");
        }
        command = rawArgs.get(0);
        int argumentIndex = 1;
        while (argumentIndex < rawArgs.size()) {
            String argumentName = rawArgs.get(argumentIndex++);
            String argumentValue = rawArgs.get(argumentIndex++);
            addParameterValue(argumentName, argumentValue);
        }
    }

    public void addParameterValue(String argumentName, Object argumentValue) throws Exception {
        if (!argumentName.startsWith("-")) {
            throw new JascException("arguments must start with '-' character");
        }
        argumentName = argumentName.substring(1);
        Object existing = arguments.get(argumentName);
        if (existing != null) {
            throw new JascException("argument " + argumentName + ALREADY_SPECIFIED);
        }
        arguments.put(argumentName, argumentValue);
    }

    public String getCommand() {
        return command;
    }

    public Object getArgumentValue(String argumentName) {
        return arguments.get(argumentName);
    }

    public void usageReset() {
        notUsedArguments = new HashSet<>(arguments.keySet());
    }

    public void usageMark(String name) {
        notUsedArguments.remove(name);
    }

    public void usageVerify() throws Exception {
        if (notUsedArguments.isEmpty()) {
            return;
        }
        throw new JascException(INVALID_ARGUMENTS + notUsedArguments);
    }

    @Override
    public String toString() {
        return "Arguments{" +
                "command='" + command + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
