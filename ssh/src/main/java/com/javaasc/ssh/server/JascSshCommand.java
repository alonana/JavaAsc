package com.javaasc.ssh.server;

import com.javaasc.shell.api.ShellConnectionImpl;
import com.javaasc.util.JascLogger;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JascSshCommand implements Command, Runnable {
    private static final JascLogger logger = JascLogger.getLogger(JascSshCommand.class);

    private final JascSshServer jascSshServer;
    private InputStream in;
    private OutputStream out;
    private OutputStream err;
    private ExitCallback exitCallback;

    public JascSshCommand(JascSshServer jascSshServer) {
        this.jascSshServer = jascSshServer;
    }

    public void setInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void setErrorStream(OutputStream errorStream) {
        this.err = errorStream;
    }

    public void setExitCallback(ExitCallback exitCallback) {
        this.exitCallback = exitCallback;
    }

    public void start(Environment environment) throws IOException {
        new Thread(this).start();
    }

    public void destroy() throws Exception {
        logger.debug("command destroy");
    }

    public void run() {
        try {
            ShellConnectionImpl connection = new ShellConnectionImpl(in, out, err);
            jascSshServer.handle(connection);
        } catch (Exception e) {
            logger.warn("handling SSH connection failed",e);
        }
    }
}
