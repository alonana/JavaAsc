package com.javaasc.entity;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.api.JascOption;
import com.javaasc.entity.core.Arguments;
import com.javaasc.entity.core.JascEntities;
import com.javaasc.entity.core.MethodInformation;
import com.javaasc.entity.core.ParameterInformation;
import com.javaasc.test.JascTest;

public class EntityTest extends JascTest {

    @Override
    public void test() throws Exception {
        JascEntities manager = JascEntities.INSTANCE;
        manager.addClass(this.getClass());

        checkWithExpectedError("missing annotation", () -> manager.addClass(EntityWithoutParemeterAnnotation.class));

        checkWithExpectedError(JascEntities.COMMAND_NOT_FOUND, () -> {
            Arguments args = new Arguments("nonExisting");
            manager.execute(args);
        });

        Arguments arguments = new Arguments("methodWithoutArguments");
        manager.execute(arguments);

        arguments = new Arguments("methodWithoutArgumentsRenamed2");
        manager.execute(arguments);

        checkWithExpectedError(ParameterInformation.MISSING_VALUE_FOR_PARAMETER, () -> {
            Arguments args = new Arguments("methodWithArgumentString");
            manager.execute(args);
        });

        arguments = new Arguments("methodWithArgumentString");
        arguments.addParameterValue("input1", "value1");
        manager.execute(arguments);

        arguments = new Arguments("methodWithArgumentInteger");
        arguments.addParameterValue("input1", 4);
        manager.execute(arguments);

        checkWithExpectedError(MethodInformation.ERROR_RUNNING, () -> {
            Arguments args = new Arguments("methodWithArgumentInteger");
            args.addParameterValue("input1", "value1");
            manager.execute(args);
        });
    }

    @SuppressWarnings("unused")
    @JascOperation()
    public void methodWithoutArguments() throws Exception {
        print("methodWithoutArguments was run");
    }

    @SuppressWarnings("unused")
    @JascOperation(name = "methodWithoutArgumentsRenamed2")
    public void methodWithoutArgumentsRenamed1() throws Exception {
        print("methodWithoutArgumentsRenamed was run");
    }

    @SuppressWarnings("unused")
    @JascOperation()
    public void methodWithArgumentString(@JascOption(name = "input1") String input1) throws Exception {
        print("methodWithArgumentString was run " + input1);
    }

    @SuppressWarnings("unused")
    @JascOperation()
    public void methodWithArgumentInteger(@JascOption(name = "input1") Integer input1) throws Exception {
        print("methodWithArgumentInteger was run " + input1);
    }
}
