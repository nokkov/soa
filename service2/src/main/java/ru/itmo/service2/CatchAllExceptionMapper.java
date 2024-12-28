package ru.itmo.service2;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    @Produces(MediaType.APPLICATION_XML)
    public Response toResponse(Exception e) {
        if (e instanceof WebApplicationException w) {
            return Response.status(w.getResponse().getStatus()).entity(new ErrorPayload(w.getMessage())).type(MediaType.APPLICATION_XML).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorPayload(e.getMessage())).build();
    }
}
