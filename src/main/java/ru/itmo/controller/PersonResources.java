package ru.itmo.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.model.Person;

@Path("/persons")
public class PersonResources {
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPerson(Person person) {
        return Response.status(Response.Status.CREATED).entity(person).build();
    }

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") Long id) {
        Person person = new Person();
        return Response.ok(person).build();
    }

    @PATCH
    @Path("/{id}")
    public Response updatePerson(@PathParam("id") Long id, Person person) {
        return Response.ok(person).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Long id) {
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
