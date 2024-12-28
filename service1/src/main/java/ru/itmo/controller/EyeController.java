package ru.itmo.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.model.Color;

@Path("/eye-color")
@Transactional
public class EyeController {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getEyeColors() {
        StringBuilder result = new StringBuilder("<colors>");
        for (Color color : Color.values()) {
            result.append("<color>").append(color.name()).append("</color>");
        }
        return Response.ok(result.append("</colors>").toString()).build();
    }
}