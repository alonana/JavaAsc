package com.javaasc.telnet.server;

import net.wimpi.telnetd.io.BasicTerminalIO;
import net.wimpi.telnetd.net.Connection;

import java.io.IOException;
import java.io.OutputStream;

public class TerminalOutputStream extends OutputStream {
    private final BasicTerminalIO terminalIO;

    public TerminalOutputStream(Connection connection) {
        terminalIO = connection.getTerminalIO();
    }

    @Override
    public void write(int i) throws IOException {
        terminalIO.write((byte) i);
    }
}
