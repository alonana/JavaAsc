package com.javaasc.test;

import org.junit.Test;

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
}
