package com.javaasc.ssh;

import org.apache.sshd.common.Factory;
import org.apache.sshd.server.Command;

public class MyShellFactory implements Factory<Command> {
    public Command create() {
        return new MyCommand();
    }
}
