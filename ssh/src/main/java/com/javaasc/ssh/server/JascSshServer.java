package com.javaasc.ssh.server;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;

import java.io.File;

public class JascSshServer {
    //TODO: allow to configure port, and security, see https://mina.apache.org/sshd-project/configuring_security.html
    public void start() throws Exception {
        SshServer server = SshServer.setUpDefaultServer();
        server.setPasswordAuthenticator(new MyPaswordAuthenticator());
        server.setPort(12312);
        server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("target/hostkey.ser")));
        server.setShellFactory(new MyShellFactory());
        server.start();
    }
}

