package com.javaasc.ssh;

import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyCommand implements Command, Runnable {
    private InputStream inputStream;
    private OutputStream outputStream;
    private OutputStream errorStream;
    private ExitCallback exitCallback;

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setErrorStream(OutputStream errorStream) {
        this.errorStream = errorStream;
    }

    public void setExitCallback(ExitCallback exitCallback) {
        this.exitCallback = exitCallback;
    }

    public void start(Environment environment) throws IOException {
        new Thread(this).start();
    }

    public void destroy() throws Exception {
        System.out.println("command started");
    }

    public void run() {
        try {
            outputStream.write("Welcome\n".getBytes());

            System.out.println("command started");
            byte[] buffer = new byte[1024];
            while (true) {
                System.out.println("read1");
                int n = inputStream.read(buffer);
                System.out.println("read2");
                outputStream.write(buffer, 0, n);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
