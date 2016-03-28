package com.javaasc.ssh.server;

import org.apache.sshd.common.Factory;
import org.apache.sshd.server.Command;

public class MyShellFactory implements Factory<Command> {
    public Command create() {
        return new MyCommand();
    }
}
