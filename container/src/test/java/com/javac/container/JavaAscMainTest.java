package com.javac.container;

import com.javaasc.container.JavaAscMain;
import com.javaasc.test.TestUtil;
import org.junit.Test;

public class JavaAscMainTest {
    @Test(timeout = TestUtil.DEFAULT_TIMEOUT)
    public void test() throws Exception {
        new JavaAscMain().run();
        Thread.sleep(600 * 1000);
    }
}
