package com.javaasc.web.server;

import com.javaasc.entity.core.Arguments;
import com.javaasc.entity.core.JascEntities;
import com.javaasc.util.CollectionUtil;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("entities")
public class RestEntity {
    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAll() {
        List<String> names = JascEntities.INSTANCE.getOperationsNames();
        return CollectionUtil.getNiceList(names);
    }

    @POST
    @Path("/{entity}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String get(@PathParam("entity") String entity, String payload) throws Exception {
        Arguments arguments = new Arguments(entity);

        JSONObject root = new JSONObject(payload);
        for (String key : root.keySet()) {
            String value = root.getString(key);
            arguments.addParameterValue(key, value, false);
        }
        return JascEntities.INSTANCE.execute(arguments);
    }
}
