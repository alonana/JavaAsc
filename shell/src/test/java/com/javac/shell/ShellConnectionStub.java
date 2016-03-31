package com.javac.shell;

import com.javaasc.shell.api.ShellConnection;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class ShellConnectionStub implements ShellConnection {
    private final PipedOutputStream stdout;
    private final PipedInputStream stdoutReader;
    private final PipedInputStream stdin;
    private final PipedOutputStream stdinWriter;

    public ShellConnectionStub() throws Exception {
        stdout = new PipedOutputStream();
        stdoutReader = new PipedInputStream(stdout);
        stdin = new PipedInputStream();
        stdinWriter = new PipedOutputStream(stdin);
    }

    @Override
    public InputStream getInputStream() throws Exception {
        return stdin;
    }

    @Override
    public OutputStream getOutputStream() throws Exception {
        return stdout;
    }

    @Override
    public OutputStream getErrorStream() throws Exception {
        return null;
    }

    public PipedOutputStream getStdinWriter() {
        return stdinWriter;
    }

    public PipedInputStream getStdoutReader() {
        return stdoutReader;
    }
}
