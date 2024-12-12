package ru.itmo.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
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
    @Consumes("application/json")
    @Produces("application/json")
    public Response updatePerson(@PathParam("id") Long id, Person updatedPerson) {
        Person existingPerson = em.find(Person.class, id);
        if (existingPerson == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        existingPerson.setName(updatedPerson.getName());
        existingPerson.setCoordinates(updatedPerson.getCoordinates());
        existingPerson.setHeight(updatedPerson.getHeight());
        existingPerson.setBirthday(updatedPerson.getBirthday());
        existingPerson.setWeight(updatedPerson.getWeight());
        existingPerson.setHairColor(updatedPerson.getHairColor());
        existingPerson.setLocation(updatedPerson.getLocation());

        em.merge(existingPerson);

        return Response.ok(existingPerson).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response deletePerson(@PathParam("id") Long id) {
        Person person = em.find(Person.class, id);
        if (person == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        em.remove(person);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count-by-location")
    @Produces("application/json")
    public Response countByLocation(@QueryParam("threshold") Double threshold) {
        Long count = em.createQuery(
                "SELECT COUNT(p) FROM Person p WHERE p.location.x * p.location.y * p.location.z > :threshold", Long.class)
                .setParameter("threshold", threshold)
                .getSingleResult();

        return Response.ok(count).build();
    }
}
