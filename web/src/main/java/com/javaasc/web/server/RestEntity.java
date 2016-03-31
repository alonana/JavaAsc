package com.javaasc.web.server;

import com.javaasc.entity.core.Arguments;
import com.javaasc.entity.core.ClassAnalyzer;
import com.javaasc.util.CollectionUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

@Path("entities")
public class RestEntity {
    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAll() {
        List<String> names = ClassAnalyzer.INSTANCE.getOperationsNames();
        return CollectionUtil.getNiceList(names);
    }

    @GET
    @Path("{entity}")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("entity") String entity) throws Exception {
        List<String> args = new LinkedList<>();
        args.add(entity);
        Arguments arguments = new Arguments(args);
        return ClassAnalyzer.INSTANCE.execute(arguments);
    }
}
