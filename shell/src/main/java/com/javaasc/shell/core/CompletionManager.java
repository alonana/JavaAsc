package com.javaasc.shell.core;

import com.javaasc.entity.com.javaasc.entity.core.ClassAnalyzer;
import com.javaasc.util.JascLogger;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class CompletionManager {
    private static final JascLogger logger = JascLogger.getLogger(CompletionManager.class);

    private ShellPromptLine promptLine;
    private final Shell shell;
    private final LinkedList<String> available;
    private String prefix;

    public CompletionManager(ShellPromptLine promptLine, Shell shell) {
        this.promptLine = promptLine;
        this.shell = shell;
        available = new LinkedList<>();
    }

    public void complete() {
        String command = promptLine.getCommandUntilCursor();
        command = command.replaceAll("^\\s+", "");

        if (!command.contains(" ")) {
            listCommands(command);
        }
        updateResult();
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
