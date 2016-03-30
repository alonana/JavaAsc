package com.javaasc.shell.api;

import com.javaasc.util.JascLogger;
import com.javaasc.util.StreamUtil;

public class ShellConnectionHandlerMirror implements ShellConnectionHandler {
    private static final JascLogger logger = JascLogger.getLogger(ShellConnectionHandlerMirror.class);

    public void handleConnection(ShellConnection connection) throws Exception {
        logger.debug("got new connection");
        StreamUtil.copy(connection.getInputStream(), connection.getOutputStream());
    }
}
