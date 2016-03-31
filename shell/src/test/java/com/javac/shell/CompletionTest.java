package com.javac.shell;

import com.javaasc.entity.com.javaasc.entity.core.ClassAnalyzer;
import com.javaasc.shell.core.CompletionManager;
import com.javaasc.shell.core.Shell;
import com.javaasc.test.JascTest;
import com.javaasc.util.StreamUtil;
import org.junit.Assert;

public class CompletionTest extends JascTest {
    public static final String BEEP = "\u0007";

    @Override
    public void test() throws Exception {
        checkCommands();
        checkArgumentsSingle();
        checkArgumentsMultiple();
        checkValues();
    }

    private void checkCommands() throws Exception {
        check("",
                "\r\nanother\r\ncheckArgs1\r\ncheckArgs2\r\ncheckValues\r\noperation1\r\noperation3\r\n",
                "");

        check("a",
                "",
                "another ");

        check("o",
                "\r\noperation1\r\noperation3\r\n",
                "o");

        check("operation1",
                "",
                "operation1 ");

        check("x",
                BEEP,
                "x");
    }

    private void checkArgumentsSingle() throws Exception {
        check("checkArgs1 ",
                "",
                "checkArgs1 -argument1 ");

        check("checkArgs1 -",
                "",
                "checkArgs1 -argument1 ");

        check("checkArgs1 -a",
                "",
                "checkArgs1 -argument1 ");

        check("checkArgs1 -argument1",
                "",
                "checkArgs1 -argument1 ");

        check("checkArgs1 -argument1 ",
                BEEP,
                "checkArgs1 -argument1 ");

        check("checkArgs1 -argument1 value",
                BEEP,
                "checkArgs1 -argument1 value");

        check("checkArgs1 -x",
                BEEP,
                "checkArgs1 -x");
    }

    private void checkArgumentsMultiple() throws Exception {
        check("checkArgs2 ",
                "\r\n-a1a\r\n-a2a\r\n-bbb\r\n",
                "checkArgs2 ");

        check("checkArgs2 -",
                "\r\n-a1a\r\n-a2a\r\n-bbb\r\n",
                "checkArgs2 -");

        check("checkArgs2 -b",
                "",
                "checkArgs2 -bbb ");

        check("checkArgs2 -a",
                "\r\n-a1a\r\n-a2a\r\n",
                "checkArgs2 -a");

        check("checkArgs2 -a1",
                "",
                "checkArgs2 -a1a ");
    }

    private void checkValues() throws Exception {
        check("checkValues -a1 ",
                "\r\nvalue1111\r\nvalue1122\r\n",
                "checkValues -a1 ");

        check("checkValues -a1 value",
                "\r\nvalue1111\r\nvalue1122\r\n",
                "checkValues -a1 value");

        check("checkValues -a1 value111",
                "",
                "checkValues -a1 value1111 ");

        check("checkValues -a1 x",
                BEEP,
                "checkValues -a1 x");
    }

    private void check(String input, String expectedOutput, String expectedPrompt) throws Exception {
        ClassAnalyzer.INSTANCE.clear();
        ClassAnalyzer.INSTANCE.analyzeClass(OperationStub.class);
        ShellConnectionStub connectionStub = new ShellConnectionStub();
        connectionStub.getStdinWriter().write(input.getBytes());

        Shell shell = new Shell(connectionStub);
        waitForCompletion(connectionStub, shell);

        CompletionManager manager = new CompletionManager(shell);
        manager.complete();

        waitForCompletion(connectionStub, shell);

        connectionStub.getOutputStream().close();
        String output = StreamUtil.loadToString(connectionStub.getStdoutReader());
        String prompt = shell.getPromptLine().getAll();
        String fullExpectedOutput = "";
        StringBuilder typing = new StringBuilder();
        for (char c : input.toCharArray()) {
            typing.append(c);
            fullExpectedOutput += "\r" + shell.getPromptLine().getPrompt() + typing + Shell.DELETE_ADDITIONAL;
        }
        fullExpectedOutput += expectedOutput;
        String fullExpectedPrompt = shell.getPromptLine().getPrompt() + expectedPrompt;
        print("\noutput is:\n" + output);
        print("\nfull expected output is:\n" + fullExpectedOutput);
        print("\nprompt is:\n" + prompt);
        Assert.assertEquals("output", fullExpectedOutput, output);
        Assert.assertEquals("prompt", fullExpectedPrompt, prompt);
    }

    private void waitForCompletion(ShellConnectionStub connectionStub, Shell shell) throws Exception {
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
