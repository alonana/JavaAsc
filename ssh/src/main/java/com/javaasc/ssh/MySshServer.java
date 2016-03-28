package com.javaasc.ssh;

import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;

import java.io.File;

public class MySshServer {
    public static void main(String[] args) throws Exception {
        org.apache.sshd.server.SshServer server = org.apache.sshd.server.SshServer.setUpDefaultServer();
        server.setPasswordAuthenticator(new MyPaswordAuthenticator());
        server.setPort(12312);
        server.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("target/hostkey.ser")));
        server.setShellFactory(new MyShellFactory());
        server.start();
        Object o = new Object();
        synchronized (o) {
            o.wait();
        }
    }
}

