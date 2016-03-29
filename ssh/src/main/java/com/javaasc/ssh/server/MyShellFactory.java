package com.javaasc.ssh.server;

import org.apache.sshd.common.Factory;
import org.apache.sshd.server.Command;

public class MyShellFactory implements Factory<Command> {
    private JascSshServer jascSshServer;

    public MyShellFactory(JascSshServer jascSshServer) {
        this.jascSshServer = jascSshServer;
    }

    public Command create() {
        return new JascSshCommand(jascSshServer);
    }
}
