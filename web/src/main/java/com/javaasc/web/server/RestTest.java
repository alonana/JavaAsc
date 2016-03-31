package com.javaasc.web.server;

import com.javaasc.util.JascException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("test")
public class RestTest {
    @Path("ping")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "Jasc server running!";
    }

    @Path("error")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String error() {
        throw new JascException("my error");
    }
}
