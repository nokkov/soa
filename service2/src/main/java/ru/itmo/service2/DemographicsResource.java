package ru.itmo.service2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import java.util.Iterator;
import java.util.logging.Logger;

@Path("/demography")
public class DemographicsResource {
    //private static final String API_URL = "https://localhost:8443/soa-1.0-SNAPSHOT/api";
    private static final String API_URL = "http://localhost:8080/soa-1.0-SNAPSHOT/api";

    private final Logger log = Logger.getLogger(DemographicsResource.class.getName());

    private Client client = null;
    private XmlMapper mapper = null;

    @PostConstruct
    private void postConstruct() {
        client = ClientBuilder.newBuilder()
            .register(PassthroughXmlReader.class)
            .withConfig((new ClientConfig())
                .property(ClientProperties.CONNECT_TIMEOUT, 500)
                .property(ClientProperties.READ_TIMEOUT, 500))
            .build();
        mapper = new XmlMapper();
    }

    @PreDestroy
    private void preDestroy() {
        if (client != null) {
            client.close();
        }
    }

    @GET
    @Path("/eye-color/{eye-color}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response eyeColorDemographics(@PathParam("eye-color") String eyeColor) {
        JsonNode root = getPersonsWithEyeColor(eyeColor);
        return Response.ok(countPersons(root)).build();
    }

    @GET
    @Path("/eye-color/{eye-color}/percentage")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getEyeColorPercentage(@PathParam("eye-color") String eyeColor) {
        JsonNode root = getPersonsWithEyeColor(eyeColor);
        long total = root.get("total").asLong();
        return Response.ok(total == 0 ? 1 : (double) countPersons(root) / total).build();
    }

    private long countPersons(JsonNode root) {
        long size = 0;
        for (Iterator<String> it = root.get("persons").fieldNames(); it.hasNext(); ) {
            String fieldName = it.next();
            if (fieldName.equals("person")) {
                ++size;
            } else {
                log.warning("Bad field name, not person: " + fieldName);
            }
        }
        return size;
    }

    private JsonNode getPersonsWithEyeColor(String eyeColor) {
        try {
            Response rxResp = client.target(API_URL)
                .register(PassthroughXmlReader.class)
                .path("/persons")
                .queryParam("filter", "eyeColor eq " + eyeColor)
                .request()
                .get();
            log.info(String.valueOf(rxResp.getStatus()));
            if (rxResp.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                throw new InternalServerErrorException();
            }
            String body = rxResp.readEntity(String.class);
            log.info(body);
            return mapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException(e);
        } catch (ProcessingException e) {
            log.warning(e.getMessage());
            throw new ServiceUnavailableException(e.getMessage());
        }
    }
}