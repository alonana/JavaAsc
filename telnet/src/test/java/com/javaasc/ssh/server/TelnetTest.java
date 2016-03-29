package com.javaasc.ssh.server;

import com.javaasc.shell.api.ShellConnectionHandlerToLog;
import com.javaasc.telnet.client.JascTelnetClient;
import com.javaasc.telnet.server.JascTelnetServer;
import com.javaasc.test.TestUtil;
import org.junit.Test;

public class TelnetTest {
    @Test(timeout = TestUtil.DEFAULT_TIMEOUT)
    public void test() throws Exception {
        ShellConnectionHandlerToLog handler = new ShellConnectionHandlerToLog();
        JascTelnetServer server = new JascTelnetServer(handler);
        server.start();

        JascTelnetClient client = new JascTelnetClient("localhost", 5000);
        client.connect();
        client.getOutputStream().write("aaa".getBytes());
        byte buffer[] = new byte[1024];
        int n = client.getInputStream().read(buffer);
        if (n > 0) {
            String result = new String(buffer, 0, n);
            System.out.println(result);
        }
        client.close();
    }
}
