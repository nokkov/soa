package ru.itmo.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import ru.itmo.model.Person;

import java.net.URI;

@Path("/persons")
@Transactional
public class PersonResources {
    @PersistenceContext(unitName = "testPU")
    private EntityManager em;

    @POST
    @Consumes("application/json")
    public Response addPerson(Person person, @Context UriInfo uriContext) {
        em.persist(person);
        URI uri = uriContext.getAbsolutePathBuilder().path("{id}").build(person.getId());
        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getPerson(@PathParam("id") Long id) {
        Person person = em.find(Person.class, id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
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
