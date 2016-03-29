package com.javaasc.shell.core;

public class ShellWriter implements Runnable{
    private Shell shell;

    public ShellWriter(Shell shell) {
        this.shell = shell;
    }

    public void run() {
        shell.writerThread();
    }
}
