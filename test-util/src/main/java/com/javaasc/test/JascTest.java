package com.javaasc.test;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract public class JascTest {
    static public void print(Object o) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = dateFormat.format(new Date(System.currentTimeMillis()));
        System.out.println(time + " ===> " + o);
    }

    @Test(timeout = TestUtil.DEFAULT_TIMEOUT)
    abstract public void test() throws Exception;

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
