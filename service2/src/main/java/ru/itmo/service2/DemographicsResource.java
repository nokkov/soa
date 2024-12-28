package ru.itmo.service2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/demography")
public class DemographicsResource {
    //private static final String API_URL = "https://localhost:8443/soa-1.0-SNAPSHOT/api";
    private static final String API_URL = "http://localhost:8080/soa-1.0-SNAPSHOT/api";

    private Client client = null;
    private ObjectMapper mapper = null;

    @PostConstruct
    private void postConstruct() {
        client = ClientBuilder.newClient();
        mapper = new ObjectMapper();
    }

    @PreDestroy
    private void preDestroy() {
        if (client != null) {
            client.close();
        }
    }

    @GET
    @Path("/eye-color/{eye-color}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eyeColorDemographics(@PathParam("eye-color") String eyeColor) {
        JsonNode root = getPersonsWithEyeColor(eyeColor);
        ArrayNode persons = (ArrayNode) root.get("persons");
        return Response.ok(persons.size()).build();
    }

    @GET
    @Path("/eye-color/{eye-color}/percentage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEyeColorPercentage(@PathParam("eye-color") String eyeColor) {
        JsonNode root = getPersonsWithEyeColor(eyeColor);
        long total = root.get("total").asLong();
        ArrayNode persons = (ArrayNode) root.get("persons");
        return Response.ok(total == 0 ? 1 : (double) persons.size() / total).build();
    }

    private JsonNode getPersonsWithEyeColor(String eyeColor) {
        Response rxResp = client.target(API_URL)
            .path("/persons")
            .queryParam("filter", "hairColor eq " + eyeColor)
            .request().get();
        if (rxResp.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            throw new InternalServerErrorException();
        }
        try {
            return mapper.readTree(rxResp.readEntity(String.class));
        } catch (JsonProcessingException exception) {
            throw new InternalServerErrorException(exception);
        }
    }
}