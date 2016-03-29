package com.javaasc.telnet.server;

import net.wimpi.telnetd.io.BasicTerminalIO;
import net.wimpi.telnetd.net.Connection;

import java.io.IOException;
import java.io.InputStream;

public class TerminalInputStream extends InputStream{
    private final BasicTerminalIO terminalIO;

    public TerminalInputStream(Connection connection) {
        terminalIO = connection.getTerminalIO();
    }

    @Override
    public int read() throws IOException {
        return terminalIO.read();
    }
}
