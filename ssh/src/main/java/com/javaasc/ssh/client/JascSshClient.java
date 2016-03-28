package com.javaasc.ssh.client;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class JascSshClient {
    private final String host;
    private final int port;
    private String user;

    public JascSshClient(String host, int port, String user) {
        this.host = host;
        this.port = port;
        this.user = user;
    }

    public void connect() throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.setPassword("dummy");
        session.connect(30000);
        Channel channel = session.openChannel("shell");
        channel.setInputStream(System.in);
        channel.setOutputStream(System.out);
        channel.connect(3 * 1000);
    }
}
