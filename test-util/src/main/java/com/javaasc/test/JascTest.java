package com.javaasc.test;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract public class JascTest {

    private final SimpleDateFormat dateFormat;

    public JascTest() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }

    @Test(timeout = TestUtil.DEFAULT_TIMEOUT)
    abstract public void test() throws Exception;

    public void print(Object o) {
        String time = dateFormat.format(new Date(System.currentTimeMillis()));
        System.out.println(time + " ===> " + o);
    }

    public void checkWithExpectedError(String expectedErrorMessage, RunnableWithException runnable) throws Exception {
        try {
            runnable.run();
        } catch (Throwable e) {
            if (e.getMessage().contains(expectedErrorMessage)) {
                StringWriter writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                e.printStackTrace(printWriter);
                print("Expected error\n" + writer.toString());
                return;
            }
            throw new Exception("error does not contains expected message: " + expectedErrorMessage, e);
        }
        throw new Exception("exception was not thrown");
    }
}
