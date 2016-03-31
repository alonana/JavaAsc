package com.javaasc.web.client;

import com.javaasc.util.JascException;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class WebClient {
    private final Client client;

    public WebClient(boolean log) {
        ClientConfig config = new ClientConfig();
        if (log) {
            config = config.register(LoggingFilter.class);
        }
        client = ClientBuilder.newClient(config);
    }

    public Response get(String url, String path) {
        return client
                .target(url)
                .path(path)
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE, MediaType.TEXT_PLAIN_TYPE)
                .get();
    }

    public String getSimple(String url, String path) {
        Response response = get(url, path);
        String result = response.readEntity(String.class);
        if (response.getStatus() != 200) {
            throw new JascException("response error " + response.getStatus() + ":" + response.getStatusInfo() +
                    " " + result);
        }
        return result;
    }
}
