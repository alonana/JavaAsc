package com.javaasc.shell.core;

import com.javaasc.entity.core.JascEntities;
import com.javaasc.entity.core.MethodInformation;
import com.javaasc.entity.core.ParameterInformation;
import com.javaasc.shell.core.command.CommandParser;
import com.javaasc.util.JascLogger;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class CompletionManager {
    private static final JascLogger logger = JascLogger.getLogger(CompletionManager.class);

    private final Shell shell;
    private final LinkedList<String> available;
    private final CommandParser parser;
    private final String commandUntilCursor;
    private String prefix;

    public CompletionManager(Shell shell) throws Exception {
        this.shell = shell;
        available = new LinkedList<>();
        String untilCursor = shell.getPromptLine().getCommandUntilCursor();
        commandUntilCursor = untilCursor.replaceAll("^\\s+", "");
        parser = new CommandParser(commandUntilCursor, true);
    }

    public void complete() throws Exception {
        logger.debug("completing for {}", commandUntilCursor);
        if (parser.getRawArguments().size() <= 1 && !commandUntilCursor.endsWith(" ")) {
            listCommands(commandUntilCursor);
        } else if (parser.getRawArguments().size() > 0) {
            listArguments();
        }
        updateResult();
    }

    private void listArguments() throws Exception {
        String operationName = parser.getRawArguments().getFirst();
        MethodInformation operation = JascEntities.INSTANCE.getOperation(operationName);
        if (operation == null) {
            return;
        }

        if (parser.getRawArguments().size() == 1) {
            prefix = "";
            available.addAll(operation.getParametersNames(true));
            Collections.sort(available);
        } else if (parser.getRawArguments().getLast().startsWith("-") && !commandUntilCursor.endsWith(" ")) {
            prefix = parser.getRawArguments().getLast();
            available.addAll(operation.getParametersNames(true));
            Collections.sort(available);
        } else {
            listValues(operation);
        }
    }

    private void listValues(MethodInformation operation) throws Exception {
        String argument;
        if (commandUntilCursor.endsWith(" ")) {
            if (!parser.getRawArguments().getLast().startsWith("-")) {
                return;
            }
            argument = parser.getRawArguments().getLast();
            prefix = "";
        } else {
            if (parser.getRawArguments().size() < 3) {
                return;
            }
            prefix = parser.getRawArguments().getLast();
            argument = parser.getRawArguments().get(parser.getRawArguments().size() - 2);
            if (!argument.startsWith("-")) {
                return;
            }
        }
        logger.debug("check completer for argument {} prefix {}", argument, prefix);
        ParameterInformation option = operation.getParameter(argument, true);
        if (option == null) {
            return;
        }
        available.addAll(option.getValues());
    }

    private void updateResult() {
        logger.debug("available completions: " + available);

        Iterator<String> iterator = available.iterator();
        while (iterator.hasNext()) {
            String current = iterator.next();
            if (!current.startsWith(prefix)) {
                iterator.remove();
            }
        }
        if (available.isEmpty()) {
            shell.addText(EscapeHandlingStream.BELL);
            return;
        }
        if (available.size() == 1) {
            String selected = available.getFirst() + " ";
            selected = selected.substring(prefix.length());
            shell.getPromptLine().add(selected);
            return;
        }
        for (String s : available) {
            shell.addText("\r\n" + s);
        }
        shell.addText("\r\n");
    }

    private void listCommands(String command) {
        prefix = command;
        available.addAll(JascEntities.INSTANCE.getOperationsNames());
        Collections.sort(available);
    }
}
