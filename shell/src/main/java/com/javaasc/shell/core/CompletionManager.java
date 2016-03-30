package com.javaasc.shell.core;

import com.javaasc.entity.com.javaasc.entity.core.ClassAnalyzer;
import com.javaasc.entity.com.javaasc.entity.core.MethodInformation;
import com.javaasc.shell.core.command.CommandParser;
import com.javaasc.util.JascLogger;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class CompletionManager {
    private static final JascLogger logger = JascLogger.getLogger(CompletionManager.class);

    private final ShellPromptLine promptLine;
    private final Shell shell;
    private final LinkedList<String> available;
    private final CommandParser parser;
    private final String commandUntilCursor;
    private String prefix;

    public CompletionManager(ShellPromptLine promptLine, Shell shell) throws Exception {
        this.promptLine = promptLine;
        this.shell = shell;
        available = new LinkedList<>();
        String untilCursor = promptLine.getCommandUntilCursor();
        commandUntilCursor = untilCursor.replaceAll("^\\s+", "");
        parser = new CommandParser(commandUntilCursor, true);
    }

    public void complete() throws Exception {
        if (parser.getRawArguments().size() <= 1 && !commandUntilCursor.endsWith(" ")) {
            listCommands(commandUntilCursor);
        } else if (parser.getRawArguments().size() > 0) {
            listArguments();
        }
        updateResult();
    }

    private void listArguments() {
        String operationName = parser.getRawArguments().getFirst();
        MethodInformation operation = ClassAnalyzer.INSTANCE.getOperation(operationName);
        if (operation == null) {
            return;
        }

        if (parser.getRawArguments().size() == 1) {
            prefix = "";
            available.addAll(operation.getOptionsNames());
        } else if (parser.getRawArguments().getLast().startsWith("-") && !commandUntilCursor.endsWith(" ")) {
            prefix = parser.getRawArguments().getLast();
            available.addAll(operation.getOptionsNames());
        }
    }

    private void updateResult() {
        Collections.sort(available);
        logger.debug("available completions: " + available);

        Iterator<String> iterator = available.iterator();
        while (iterator.hasNext()) {
            String current = iterator.next();
            if (!current.startsWith(prefix)) {
                iterator.remove();
            }
        }
        if (available.isEmpty()) {
            shell.addText(EscapeHandlingStream.BEEP);
            return;
        }
        if (available.size() == 1) {
            String selected = available.getFirst() + " ";
            selected = selected.substring(prefix.length());
            promptLine.add(selected);
            return;
        }
        for (String s : available) {
            shell.addText("\r\n" + s);
        }
        shell.addText("\r\n");
    }

    private void listCommands(String command) {
        prefix = command;
        available.addAll(ClassAnalyzer.INSTANCE.getOperationsNames());
    }
}
