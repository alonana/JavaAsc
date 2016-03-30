package com.javaasc.entity.com.javaasc.entity.core;

import com.javaasc.util.JascException;

import java.util.*;

public class Arguments {
    private String command;
    private HashMap<String, LinkedList<String>> arguments;
    private HashSet<String> notUsedArguments;

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
            setArgument(argumentName, argumentValue);
        }
    }

    private void setArgument(String argumentName, String argumentValue) throws Exception {
        LinkedList<String> values = arguments.get(argumentName);
        if (values == null) {
            values = new LinkedList<>();
            arguments.put(argumentName, values);
        }
        values.add(argumentValue);
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArgumentList(String argumentName) {
        return arguments.get(argumentName);
    }

    public String getArgumentSimple(String argumentName) {
        List<String> values = getArgumentList(argumentName);
        if (values == null) {
            return null;
        }
        return values.get(0);
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
        throw new JascException("invalid arguments: " + notUsedArguments);
    }

    @Override
    public String toString() {
        return "Arguments{" +
                "command='" + command + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
