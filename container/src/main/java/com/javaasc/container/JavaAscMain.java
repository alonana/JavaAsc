package com.javaasc.container;

import com.javaasc.container.operations.OperationDate;
import com.javaasc.container.operations.OperationEcho;
import com.javaasc.container.operations.OperationInfo;
import com.javaasc.container.operations.OperationListOperations;
import com.javaasc.entity.com.javaasc.entity.core.ClassAnalyzer;
import com.javaasc.shell.api.ShellConnection;
import com.javaasc.shell.api.ShellConnectionHandler;
import com.javaasc.shell.core.Shell;
import com.javaasc.ssh.server.JascSshServer;
import com.javaasc.telnet.server.JascTelnetServer;
import com.javaasc.util.JascLogger;

public class JavaAscMain implements ShellConnectionHandler {
    private static final JascLogger logger = JascLogger.getLogger(JavaAscMain.class);

    public static void main(String[] args) throws Exception {
        new JavaAscMain().run();
    }

    public void run() throws Exception {
        logger.info("Java ASC starting");

        ClassAnalyzer.INSTANCE.analyzeClass(OperationListOperations.class);
        ClassAnalyzer.INSTANCE.analyzeClass(OperationInfo.class);
        ClassAnalyzer.INSTANCE.analyzeClass(OperationDate.class);
        ClassAnalyzer.INSTANCE.analyzeClass(OperationEcho.class);

        logger.debug("starting connectors");
        JascSshServer sshServer = new JascSshServer(this);
        sshServer.start();

        JascTelnetServer telnetServer = new JascTelnetServer(this);
        telnetServer.start();
    }

    public void handleConnection(ShellConnection connection) throws Exception {
        Shell shell = new Shell(connection);
        shell.handle();
    }
}
