package com.javaasc.web.server;

import com.javaasc.util.JascLogger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class JascWebServer {
    public static final String URL = "http://localhost:8080/jasc/";

    private static final JascLogger logger = JascLogger.getLogger(JascWebServer.class);

    private HttpServer server;

    public void start() {
        ResourceConfig config = new ResourceConfig();
        String myPackage = this.getClass().getPackage().getName();
        config.packages(myPackage);
        config.register(LoggingFilter.class);
        URI uri = URI.create(URL);
        server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
    }

    public void stop() {
        server.shutdownNow();
    }
}
