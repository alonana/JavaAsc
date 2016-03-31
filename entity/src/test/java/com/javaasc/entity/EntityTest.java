package com.javaasc.entity;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.com.javaasc.entity.core.ClassAnalyzer;
import com.javaasc.test.JascTest;

public class EntityTest extends JascTest {

    @Override
    public void test() throws Exception {
        ClassAnalyzer.INSTANCE.analyzeClass(this.getClass());
    }

    @JascOperation()
    public void test1() throws Exception {
    }

    @JascOperation(name = "test4")
    public void test2() throws Exception {
    }
}
