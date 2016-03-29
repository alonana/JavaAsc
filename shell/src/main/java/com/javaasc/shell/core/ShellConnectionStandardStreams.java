package com.javaasc.shell.core;

import com.javaasc.shell.api.ShellConnectionImpl;

public class ShellConnectionStandardStreams extends ShellConnectionImpl {
    public ShellConnectionStandardStreams() {
        super(System.in, System.out, System.err);
    }
}
