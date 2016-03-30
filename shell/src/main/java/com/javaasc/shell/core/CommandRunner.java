package com.javaasc.shell.core;

import com.javaasc.entity.com.javaasc.entity.core.ClassAnalyzer;
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
        String command = promptLine.getCommand();
        String result;
        try {
            result = ClassAnalyzer.INSTANCE.execute(command) + "\r\n";
        } catch (Exception e) {
            logger.warn("command " + command + " run failed", e);
            result = JascException.getStackTrace(e);
        }
        shell.addText(result);
        promptLine.clear();
    }
}
