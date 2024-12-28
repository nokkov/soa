package ru.itmo.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("{any: .*}")
public class CatchAll {
    @GET
    public Response get() {
        return response();
    }

    @POST
    public Response post() {
        return response();
    }

    @PATCH
    public Response patch() {
        return response();
    }

    @PUT
    public Response put() {
        return response();
    }

    private Response response() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}