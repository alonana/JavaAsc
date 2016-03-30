package com.javaasc.ssh.server;

import com.javaasc.shell.api.ShellConnectionHandler;
import com.javaasc.shell.api.ShellConnectionImpl;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;

import java.io.File;

public class JascSshServer {
    private final ShellConnectionHandler handler;

    public JascSshServer(ShellConnectionHandler handler) {
        this.handler = handler;
    }

    public void start() throws Exception {
        SshServer server = SshServer.setUpDefaultServer();
        server.setPasswordAuthenticator(new MyPaswordAuthenticator());
        server.setPort(12312);
        server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("target/hostkey.ser")));
        server.setShellFactory(new MyShellFactory(this));
        server.start();
    }

    public void handle(ShellConnectionImpl connection) throws Exception {
        handler.handleConnection(connection);
    }
}

