package com.javaasc.entity;

import com.javaasc.test.TestUtil;
import org.junit.Test;

public class EntityTest implements JascOperation{
    @Test(timeout = TestUtil.DEFAULT_TIMEOUT)
    public void test() throws Exception {
    }

    public String name() {
        return "test";
    }

    public String invoke() throws Exception {
        return "I am done";
    }
}
