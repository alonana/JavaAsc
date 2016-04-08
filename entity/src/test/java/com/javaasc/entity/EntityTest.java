package com.javaasc.entity;

import com.javaasc.entity.api.JascOperation;
import com.javaasc.entity.api.JascParameter;
import com.javaasc.entity.core.Arguments;
import com.javaasc.entity.core.JascEntities;
import com.javaasc.entity.core.MethodInformation;
import com.javaasc.entity.core.ParameterInformation;
import com.javaasc.test.JascTest;
import com.javaasc.util.JascException;

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

        checkWithExpectedError(MethodInformation.ERROR_RUNNING, () -> {
            Arguments args = new Arguments("methodWithError");
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

        checkWithExpectedError(Arguments.ALREADY_SPECIFIED, () -> {
            Arguments args = new Arguments("methodWithArgumentString");
            args.addParameterValue("input1", "value1", false);
            args.addParameterValue("input1", "value2", false);
            manager.execute(args);
        });

        arguments = new Arguments("methodWithArgumentString");
        arguments.addParameterValue("input1", "value1", false);
        manager.execute(arguments);

        arguments = new Arguments("methodWithArgumentInteger");
        arguments.addParameterValue("input1", 4, false);
        manager.execute(arguments);

        arguments = new Arguments("methodWithArgumentInteger");
        arguments.addParameterValue("input1", "4", false);
        manager.execute(arguments);

        checkWithExpectedError(ParameterInformation.WRONG_CLASS, () -> {
            Arguments args = new Arguments("methodWithArgumentInteger");
            args.addParameterValue("input1", 4L, false);
            manager.execute(args);
        });

        arguments = new Arguments("methodWithArgumentBoolean");
        arguments.addParameterValue("input1", true, false);
        manager.execute(arguments);


        checkWithExpectedError(ParameterInformation.NOT_SUPPORTED_TYPE, () ->
                manager.addClass(EntityWithNonSupportedType.class));
    }

    @SuppressWarnings("unused")
    @JascOperation()
    public void methodWithoutArguments() throws Exception {
        print("methodWithoutArguments was run");
    }

    @SuppressWarnings("unused")
    @JascOperation()
    public void methodWithError() throws Exception {
        throw new JascException("my method invocation error");
    }

    @SuppressWarnings("unused")
    @JascOperation(name = "methodWithoutArgumentsRenamed2")
    public void methodWithoutArgumentsRenamed1() throws Exception {
        print("methodWithoutArgumentsRenamed was run");
    }

    @SuppressWarnings("unused")
    @JascOperation()
    public void methodWithArgumentString(@JascParameter(name = "input1") String input1) throws Exception {
        print("methodWithArgumentString was run " + input1);
    }

    @SuppressWarnings("unused")
    @JascOperation()
    public void methodWithArgumentInteger(@JascParameter(name = "input1") Integer input1) throws Exception {
        print("methodWithArgumentInteger was run " + input1);
    }

    @SuppressWarnings("unused")
    @JascOperation()
    public void methodWithArgumentBoolean(@JascParameter(name = "input1") Boolean input1) throws Exception {
        print("methodWithArgumentBoolean was run " + input1);
    }
}
