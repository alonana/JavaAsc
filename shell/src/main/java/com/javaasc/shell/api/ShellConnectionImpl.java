package com.javaasc.shell.api;

import java.io.InputStream;
import java.io.OutputStream;

public class ShellConnectionImpl implements ShellConnection {
    private final InputStream in;
    private final OutputStream out;
    private final OutputStream err;

    public ShellConnectionImpl(InputStream in, OutputStream out, OutputStream err) {
        this.in = in;
        this.out = out;
        this.err = err;
    }

    public InputStream getInputStream() throws Exception {
        return in;
    }

    public OutputStream getOutputStream() throws Exception {
        return out;
    }

    public OutputStream getErrorStream() throws Exception {
        return err;
    }
}
