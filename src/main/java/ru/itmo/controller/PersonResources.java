package ru.itmo.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import ru.itmo.exceptions.BadRequestException;
import ru.itmo.model.Person;
import ru.itmo.response.OkPayload;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/persons")
@Transactional
public class PersonResources {
    @PersistenceContext(unitName = "testPU")
    private EntityManager em;

    private final Logger log = Logger.getLogger(PersonResources.class.getName());

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

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response getPersons(@QueryParam("sort") List<String> sortBy,
                               @QueryParam("filter") List<String> filters,
                               @QueryParam("page") Integer page,
                               @QueryParam("pageSize") Integer pageSize) throws BadRequestException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);

        try {
            List<Order> orders = new ArrayList<>();
            for (String sortSpec : sortBy) {
                if (sortSpec.startsWith("-")) {
                    orders.add(cb.desc(root.get(sortSpec.substring(1))));
                } else {
                    orders.add(cb.asc(root.get(sortSpec)));
                }
            }
            if (!orders.isEmpty()) {
                cq.orderBy(orders);
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Bad sort spec", "sort");
        }

        for (String filterSpec : filters) {
            String[] split = filterSpec.split(" ");
            if (split.length != 3) {
                throw new BadRequestException("Bad filter spec", "filter");
            }
            String key = split[0];
            String op = split[1];
            String val = split[2];
            var keyRoot = root.get(key).as(String.class);
            cq.where(switch (op) {
                case "eq" -> cb.equal(keyRoot, val);
                case "ne" -> cb.notEqual(keyRoot, val);
                case "lt" -> cb.lessThan(keyRoot, val);
                case "lte" -> cb.lessThanOrEqualTo(keyRoot, val);
                case "gt" -> cb.greaterThan(keyRoot, val);
                case "gte" -> cb.greaterThanOrEqualTo(keyRoot, val);
                case "sub" -> cb.like(keyRoot, val);
                default -> throw new BadRequestException("Bad filter op", "filter");
            });
        }

        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        int from = page * pageSize;
        int upto = from + pageSize;

        Query query = em.createQuery(cq)
            .setFirstResult(from)
            .setMaxResults(upto - from);
        List<Person> resultList = query.getResultList();

        OkPayload response = new OkPayload();
        response.setFrom(from);
        response.setUpto(upto);
        response.setTotal(-1);
        response.setPersons(resultList);
        return Response.ok(response).build();
    }
}
