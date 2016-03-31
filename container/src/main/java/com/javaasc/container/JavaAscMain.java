package com.javaasc.container;

import com.javaasc.shell.api.ShellConnection;
import com.javaasc.shell.api.ShellConnectionHandler;
import com.javaasc.shell.core.Shell;
import com.javaasc.ssh.server.JascSshServer;
import com.javaasc.telnet.server.JascTelnetServer;
import com.javaasc.util.JascLogger;
import com.javaasc.web.server.JascWebServer;

public class JavaAscMain implements ShellConnectionHandler {
    private static final JascLogger logger = JascLogger.getLogger(JavaAscMain.class);

    public static void main(String[] args) throws Exception {
        new JavaAscMain().run();
    }

    public void run() throws Exception {
        logger.info("Java ASC starting");

        JascSshServer sshServer = new JascSshServer(this);
        sshServer.start();

        JascTelnetServer telnetServer = new JascTelnetServer(this);
        telnetServer.start();

        JascWebServer webServer = new JascWebServer();
        webServer.start();
    }

    public void handleConnection(ShellConnection connection) throws Exception {
        Shell shell = new Shell(connection);
        shell.handle();
    }
}
