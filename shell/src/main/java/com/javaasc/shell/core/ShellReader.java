package com.javaasc.shell.core;

public class ShellReader implements Runnable {
    private Shell shell;

    public ShellReader(Shell shell) {
        this.shell = shell;
    }

    public void run() {
        shell.readerThread();
    }
}
