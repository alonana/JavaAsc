package com.javaasc.web.server;

import com.javaasc.util.JascException;
import com.javaasc.util.JascLogger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JascExecptionMapper implements ExceptionMapper<Throwable> {
    private JascLogger logger = JascLogger.getLogger(JascExecptionMapper.class);

    @Override
    public Response toResponse(Throwable throwable) {
        logger.debug("error in web service", throwable);
        String stack = JascException.getStackTrace(throwable);
        return Response.status(500).entity(stack).build();
    }
}
