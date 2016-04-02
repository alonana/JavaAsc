package com.javaasc.shell;

import com.javaasc.shell.core.CompletionManager;
import com.javaasc.shell.core.Shell;
import com.javaasc.test.JascTest;
import com.javaasc.util.StreamUtil;
import org.junit.Assert;

public class ShellTestUtil {
    static public void checkCompletion(String input, String expectedOutput, String expectedPrompt) throws Exception {
        check(input, expectedOutput, expectedPrompt, true);
    }

    static public void checkExecute(String input, String expectedOutput) throws Exception {
        expectedOutput += "\r\n\r" + Shell.PROMPT + Shell.DELETE_ADDITIONAL;
        check(input + "\n", expectedOutput, "", false);
    }

    static private void check(String input, String expectedOutput, String expectedPrompt, boolean complete) throws Exception {
        ShellConnectionStub connectionStub = new ShellConnectionStub();
        connectionStub.getStdinWriter().write(input.getBytes());

        Shell shell = new Shell(connectionStub);
        waitForStreamRead(connectionStub, shell);

        if (complete) {
            CompletionManager manager = new CompletionManager(shell);
            manager.complete();
            waitForStreamRead(connectionStub, shell);
        }

        connectionStub.getOutputStream().close();
        String output = StreamUtil.loadToString(connectionStub.getStdoutReader());
        String prompt = shell.getPromptLine().getAll();
        String fullExpectedOutput = "";
        StringBuilder typing = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c == '\n') {
                continue;
            }
            typing.append(c);
            fullExpectedOutput += "\r" + shell.getPromptLine().getPrompt() + typing + Shell.DELETE_ADDITIONAL;
        }
        fullExpectedOutput += expectedOutput;
        String fullExpectedPrompt = shell.getPromptLine().getPrompt() + expectedPrompt;
        JascTest.print("\noutput is:\n" + output);
        JascTest.print("\nfull expected output is:\n" + fullExpectedOutput);
        JascTest.print("\nprompt is:\n" + prompt);
        Assert.assertEquals("output", fullExpectedOutput, output);
        Assert.assertEquals("prompt", fullExpectedPrompt, prompt);
    }

    static private void waitForStreamRead(ShellConnectionStub connectionStub, Shell shell) throws Exception {
        Thread.sleep(100);
        while (true) {
            shell.waitToComplete();
            int available = connectionStub.getInputStream().available();
            if (available == 0) {
                break;
            }
            Thread.sleep(10);
        }
    }

}
