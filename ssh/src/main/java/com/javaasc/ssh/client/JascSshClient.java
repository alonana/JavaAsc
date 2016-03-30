package com.javaasc.ssh.client;

import com.javaasc.util.StreamUtil;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelShell;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;

import java.io.*;

public class JascSshClient {
    private final String host;
    private final int port;
    private String user;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ChannelShell channel;
    private ClientSession session;
    private final SshClient client;

    public JascSshClient(String host, int port, String user) throws Exception {
        this.host = host;
        this.port = port;
        this.user = user;

        client = SshClient.setUpDefaultClient();
        client.start();
        ConnectFuture connectFuture = client.connect(user, host, port);
        connectFuture.await();
        session = connectFuture.getSession();
        channel = session.createShellChannel();

        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream channelIn = new PipedInputStream(out);
        channel.setIn(channelIn);
        outputStream = out;

        PipedOutputStream channelOut = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(channelOut);
        channel.setOut(channelOut);
        channel.setErr(channelOut);
        inputStream = in;

        channel.open();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void close() throws Exception {
        StreamUtil.closeSafe(inputStream);
        StreamUtil.closeSafe(outputStream);
        if (channel != null) {
            channel.close();
        }
        if (session != null) {
            session.close();
        }
        if (client != null) {
            client.close();
        }
    }
}
