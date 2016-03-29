package com.javac.container;

import com.javaasc.shell.core.Shell;
import com.javaasc.shell.core.ShellConnectionStandardStreams;
import com.javaasc.test.TestUtil;
import org.junit.Test;

public class ShellTest {
    @Test(timeout = TestUtil.DEFAULT_TIMEOUT)
    public void test() throws Exception {
        ShellConnectionStandardStreams connector = new ShellConnectionStandardStreams();
        Shell shell = new Shell(connector);
        //shell.handle();
    }
}
