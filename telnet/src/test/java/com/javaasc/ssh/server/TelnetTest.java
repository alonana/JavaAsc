package com.javaasc.ssh.server;

import com.javaasc.telnet.client.JascTelnetClient;
import com.javaasc.telnet.server.JascTelnetServer;
import com.javaasc.test.TestUtil;
import org.junit.Test;

public class TelnetTest {
    @Test(timeout = TestUtil.DEFAULT_TIMEOUT)
    public void test() throws Exception {
        JascTelnetServer server = new JascTelnetServer();
        server.start();

        JascTelnetClient client = new JascTelnetClient("localhost", 12312);
        client.connect();
    }
}
