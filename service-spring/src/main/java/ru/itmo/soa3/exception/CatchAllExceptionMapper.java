package ru.itmo.soa3.exception;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.bind.JAXBException;
import ru.itmo.soa3.response.BadParamsPayload;
import ru.itmo.soa3.response.ErrorPayload;

import java.util.List;
import java.util.logging.Logger;

@Provider
public class CatchAllExceptionMapper implements ExceptionMapper<Exception> {
    private final Logger log = Logger.getLogger(CatchAllExceptionMapper.class.getName());

    @Override
    @Produces(MediaType.APPLICATION_XML)
    public Response toResponse(Exception e) {
        if (e instanceof JAXBException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorPayload(e.getMessage())).build();
        }
        if (e instanceof WebApplicationException w) {
            return Response.status(w.getResponse().getStatus()).entity(new ErrorPayload(w.getMessage())).build();
        }
        if (e instanceof BadRequestException b) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new BadParamsPayload(b.getMessage(), List.of(b.getParam()))).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorPayload(e.getMessage())).build();
    }
}