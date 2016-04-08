package com.javaasc.web.server;

import com.javaasc.util.JascException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("test")
public class RestTest {
    @Path("ping")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "Jasc server running!";
    }

    @Path("echo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String ping(String input) {
        JSONObject jsonObject = new JSONObject(input);
        return ">" + jsonObject.toString();
    }

    @Path("error")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String error() {
        throw new JascException("my error");
    }
}
