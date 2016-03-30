package com.javac.container;

import com.javaasc.container.JavaAscMain;
import com.javaasc.telnet.client.JascTelnetClient;
import com.javaasc.test.JascTest;
import com.javaasc.util.StreamUtil;

public class JavaAscMainTest extends JascTest {
    public void test() throws Exception {
        new JavaAscMain().run();

        print("client connecting");
        JascTelnetClient client = new JascTelnetClient("localhost",5000);

        print("send client data");
        client.getOutputStream().write("PING".getBytes());

        print("get response");
        String response = StreamUtil.loadToString(client.getInputStream());
        print("server response is " + response);

        client.close();
    }
}
