package ru.itmo.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import ru.itmo.response.BadRequestPayload;

import java.util.List;

@Provider
public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException e) {
        BadRequestPayload payload = new BadRequestPayload(e.getMessage(), List.of(e.getParam()));
        return Response.status(Response.Status.BAD_REQUEST).entity(payload).build();
    }
}
