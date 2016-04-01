package com.javaasc.web;

import com.javaasc.entity.core.JascEntities;
import com.javaasc.test.JascTest;
import com.javaasc.util.JascException;
import com.javaasc.web.client.WebClient;
import com.javaasc.web.server.JascWebServer;

public class JascWebServerTest extends JascTest {
    private WebClient client;

    @Override
    public void test() throws Exception {
        JascEntities.INSTANCE.addPredefined();
        JascWebServer server = new JascWebServer();
        server.start();

        client = new WebClient(false);
        check("test/ping");
        try {
            check("test/error");
        } catch (Throwable e) {
            print("expected error: " + JascException.getStackTrace(e));
        }
        check("entities/all");
        check("entities/date");
        server.stop();
    }

    private void check(String path) {
        String result = client.getSimple(JascWebServer.URL, path);
        print("result for path " + path + " is: " + result);
    }
}
