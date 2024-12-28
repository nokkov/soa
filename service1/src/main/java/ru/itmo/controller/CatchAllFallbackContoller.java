package ru.itmo.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("{any: .*}")
public class CatchAllFallbackContoller {
    @GET
    public void get() {
        throwTheException();
    }

    @POST
    public void post() {
        throwTheException();
    }

    @PATCH
    public void patch() {
        throwTheException();
    }

    @PUT
    public void put() {
        throwTheException();
    }

    private void throwTheException() {
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
}