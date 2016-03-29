package com.javaasc.telnet.server;

import com.javaasc.shell.api.ShellConnectionHandler;
import com.javaasc.shell.api.ShellConnectionImpl;
import com.javaasc.util.ResourceUtil;
import net.wimpi.telnetd.io.terminal.TerminalManager;
import net.wimpi.telnetd.net.PortListener;
import net.wimpi.telnetd.shell.ShellManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class JascTelnetServer {
    private ShellConnectionHandler connectionHandler;

    public JascTelnetServer(ShellConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void start() throws Exception {
        Properties properties = new Properties();
        File file = ResourceUtil.getResourceAsFile("telnet.properties");
        properties.load(new FileInputStream(file));
        ShellManager.createShellManager(properties);
        TerminalManager.createTerminalManager(properties);
        TelnetHandler.telnetServer = this;
        PortListener listener = PortListener.createPortListener("std", properties);
        listener.start();
    }

    public void handle(ShellConnectionImpl connection) throws Exception {
        connectionHandler.handleConnection(connection);
    }
}

