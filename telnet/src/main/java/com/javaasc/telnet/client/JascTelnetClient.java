package com.javaasc.telnet.client;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.InputStream;
import java.io.OutputStream;

public class JascTelnetClient {
    private final String host;
    private final int port;
    private TelnetClient client;
    private InputStream inputStream;
    private OutputStream outputStream;

    public JascTelnetClient(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
        client = new TelnetClient();
        client.connect(host, port);
        inputStream = client.getInputStream();
        outputStream = client.getOutputStream();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void close() throws Exception {
        client.disconnect();
    }
}
