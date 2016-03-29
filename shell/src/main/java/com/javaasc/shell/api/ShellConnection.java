package com.javaasc.shell.api;

import java.io.InputStream;
import java.io.OutputStream;

public interface ShellConnection {
    InputStream getInputStream() throws Exception;

    OutputStream getOutputStream() throws Exception;

    OutputStream getErrorStream() throws Exception;
}
