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
        checkGet("test/ping");
        checkPost("test/echo", "{a:5}");
        try {
            checkGet("test/error");
        } catch (Throwable e) {
            print("expected error: " + JascException.getStackTrace(e));
        }

        checkGet("entities/all");
        checkPost("entities/date", "{}");
        checkPost("entities/echo", "{message:Hello}");
        server.stop();
    }

    private void checkGet(String path) {
        String result = client.getSimple(JascWebServer.URL, path);
        print("result for path " + path + " is: " + result);
    }

    private void checkPost(String path, String payload) {
        String result = client.postSimple(JascWebServer.URL, path, payload);
        print("result for path " + path + " is: " + result);
    }
}
