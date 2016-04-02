package com.javaasc.shell;

import com.javaasc.entity.core.JascEntities;
import com.javaasc.test.JascTest;

public class ExecutionTest extends JascTest {
    @Override
    public void test() throws Exception {
        JascEntities.INSTANCE.clear();
        JascEntities.INSTANCE.addClass(ExecutionTestOperationStub.class);

        ShellTestUtil.checkExecute("operation1 -arg1 aaa",
                "\r\n");

        ShellTestUtil.checkExecute("operation2 -arg1 true -arg2 1",
                "\r\ntrue 1");
    }
}
