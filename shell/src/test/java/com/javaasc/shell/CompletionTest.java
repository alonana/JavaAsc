package com.javaasc.shell;

import com.javaasc.entity.core.JascEntities;
import com.javaasc.test.JascTest;

public class CompletionTest extends JascTest {
    public static final String BEEP = "\u0007";

    @Override
    public void test() throws Exception {
        JascEntities.INSTANCE.clear();
        JascEntities.INSTANCE.addClass(CompletionTestOperationStub.class);

        checkCommands();
        checkArgumentsSingle();
        checkArgumentsMultiple();
        checkValues();
    }

    private void checkCommands() throws Exception {
        ShellTestUtil.checkCompletion("",
                "\r\nanother\r\ncheckArgs1\r\ncheckArgs2\r\ncheckValues\r\noperation1\r\noperation3\r\n",
                "");

        ShellTestUtil.checkCompletion("a",
                "",
                "another ");

        ShellTestUtil.checkCompletion("o",
                "\r\noperation1\r\noperation3\r\n",
                "o");

        ShellTestUtil.checkCompletion("operation1",
                "",
                "operation1 ");

        ShellTestUtil.checkCompletion("x",
                BEEP,
                "x");
    }

    private void checkArgumentsSingle() throws Exception {
        ShellTestUtil.checkCompletion("checkArgs1 ",
                "",
                "checkArgs1 -argument1 ");

        ShellTestUtil.checkCompletion("checkArgs1 -",
                "",
                "checkArgs1 -argument1 ");

        ShellTestUtil.checkCompletion("checkArgs1 -a",
                "",
                "checkArgs1 -argument1 ");

        ShellTestUtil.checkCompletion("checkArgs1 -argument1",
                "",
                "checkArgs1 -argument1 ");

        ShellTestUtil.checkCompletion("checkArgs1 -argument1 ",
                BEEP,
                "checkArgs1 -argument1 ");

        ShellTestUtil.checkCompletion("checkArgs1 -argument1 value",
                BEEP,
                "checkArgs1 -argument1 value");

        ShellTestUtil.checkCompletion("checkArgs1 -x",
                BEEP,
                "checkArgs1 -x");
    }

    private void checkArgumentsMultiple() throws Exception {
        ShellTestUtil.checkCompletion("checkArgs2 ",
                "\r\n-a1a\r\n-a2a\r\n-bbb\r\n",
                "checkArgs2 ");

        ShellTestUtil.checkCompletion("checkArgs2 -",
                "\r\n-a1a\r\n-a2a\r\n-bbb\r\n",
                "checkArgs2 -");

        ShellTestUtil.checkCompletion("checkArgs2 -b",
                "",
                "checkArgs2 -bbb ");

        ShellTestUtil.checkCompletion("checkArgs2 -a",
                "\r\n-a1a\r\n-a2a\r\n",
                "checkArgs2 -a");

        ShellTestUtil.checkCompletion("checkArgs2 -a1",
                "",
                "checkArgs2 -a1a ");
    }

    private void checkValues() throws Exception {
        ShellTestUtil.checkCompletion("checkValues -a1 ",
                "\r\nvalue1111\r\nvalue1122\r\n",
                "checkValues -a1 ");

        ShellTestUtil.checkCompletion("checkValues -a1 value",
                "\r\nvalue1111\r\nvalue1122\r\n",
                "checkValues -a1 value");

        ShellTestUtil.checkCompletion("checkValues -a1 value111",
                "",
                "checkValues -a1 value1111 ");

        ShellTestUtil.checkCompletion("checkValues -a1 x",
                BEEP,
                "checkValues -a1 x");
    }
}
