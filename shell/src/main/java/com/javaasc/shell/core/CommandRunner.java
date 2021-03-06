package com.javaasc.shell.core;

import com.javaasc.entity.core.Arguments;
import com.javaasc.entity.core.JascEntities;
import com.javaasc.shell.core.command.CommandParser;
import com.javaasc.util.JascException;
import com.javaasc.util.JascLogger;

public class CommandRunner {
    private static final JascLogger logger = JascLogger.getLogger(CommandRunner.class);

    private final ShellPromptLine promptLine;
    private final Shell shell;

    public CommandRunner(ShellPromptLine promptLine, Shell shell) {
        this.promptLine = promptLine;
        this.shell = shell;
    }

    public void execute() throws Exception {
        shell.addText("\r\n");
        String result;
        String commandLine = promptLine.getCommandLine().trim();
        promptLine.clear();
        if (commandLine.isEmpty()) {
            return;
        }
        try {
            Arguments arguments = new CommandParser(commandLine, false).getArguments();
            result = JascEntities.INSTANCE.execute(arguments);
            if (result == null) {
                result = "";
            }
            result += "\r\n";
        } catch (Exception e) {
            logger.warn("command " + commandLine + " run failed", e);
            result = JascException.getStackTrace(e);
        }
        shell.addText(result);
    }
}
