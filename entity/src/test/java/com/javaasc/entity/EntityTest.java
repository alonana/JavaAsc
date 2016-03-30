package com.javaasc.entity;

import com.javaasc.entity.com.javaasc.entity.core.ClassAnalyzer;
import com.javaasc.test.TestUtil;
import org.junit.Test;

public class EntityTest {
    @JascOperation()
    @Test(timeout = TestUtil.DEFAULT_TIMEOUT)
    public void test1() throws Exception {
        ClassAnalyzer.INSTANCE.analyzeClass(this.getClass());
    }

    @JascOperation(name = "test4")
    @Test(timeout = TestUtil.DEFAULT_TIMEOUT)
    public void test2() throws Exception {
        ClassAnalyzer.INSTANCE.analyzeClass(this.getClass());
    }
}
