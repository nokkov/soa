package ru.itmo.controller;

import java.util.Arrays;
import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/eye-color")
@Transactional
public class EyeController {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getEyeColors() {
        List<String> colors = Arrays.asList("RED", "GREEN", "BROWN", "WHITE");
        return Response.ok(colors).build();
    }
}