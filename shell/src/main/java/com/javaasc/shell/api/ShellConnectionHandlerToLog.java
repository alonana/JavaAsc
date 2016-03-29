package com.javaasc.shell.api;

import com.javaasc.util.JascLogger;

public class ShellConnectionHandlerToLog implements ShellConnectionHandler {
    private static final JascLogger logger = JascLogger.getLogger(ShellConnectionHandlerToLog.class);

    public void handleConnection(ShellConnection connection) throws Exception {
        logger.debug("got new connection");
    }
}
