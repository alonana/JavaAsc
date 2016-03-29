package com.javaasc.telnet.server;

import com.javaasc.shell.api.ShellConnectionImpl;
import com.javaasc.util.JascLogger;
import net.wimpi.telnetd.net.Connection;
import net.wimpi.telnetd.net.ConnectionEvent;
import net.wimpi.telnetd.shell.Shell;

public class TelnetHandler implements Shell {
    public static JascTelnetServer telnetServer;

    private static final JascLogger logger = JascLogger.getLogger(TelnetHandler.class);

    public void run(Connection connection) {
        try {
            connection.getTerminalIO().setAutoflushing(false);
            TerminalInputStream in = new TerminalInputStream(connection);
            TerminalOutputStream out = new TerminalOutputStream(connection);
            ShellConnectionImpl shellConnection = new ShellConnectionImpl(in, out, out);
            telnetServer.handle(shellConnection);
        } catch (Exception e) {
            logger.warn("handling telnet connection failed", e);
        }
    }

    public void connectionIdle(ConnectionEvent connectionEvent) {

    }

    public void connectionTimedOut(ConnectionEvent connectionEvent) {

    }

    public void connectionLogoutRequest(ConnectionEvent connectionEvent) {

    }

    public void connectionSentBreak(ConnectionEvent connectionEvent) {

    }

    // used by wimpi library according to telnet.properties
    @SuppressWarnings("unused")
    public static Shell createShell() {
        return new TelnetHandler();
    }
}
