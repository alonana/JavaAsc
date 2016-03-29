package com.javaasc.ssh.server;

import com.javaasc.shell.api.ShellConnectionHandlerToLog;
import com.javaasc.ssh.client.JascSshClient;
import com.javaasc.test.TestUtil;
import org.junit.Test;

public class SshTest {
    @Test(timeout = TestUtil.DEFAULT_TIMEOUT)
    public void test() throws Exception {
        ShellConnectionHandlerToLog handler = new ShellConnectionHandlerToLog();
        JascSshServer server = new JascSshServer(handler);
        server.start();

        JascSshClient client = new JascSshClient("localhost", 12312, "dummy");
        client.connect();
    }
}
