package ru.itmo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import ru.itmo.model.Color;
import ru.itmo.model.Coordinates;
import ru.itmo.model.Location;
import ru.itmo.model.Person;

import java.util.Date;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class SoaApplicationTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    private final Logger log = Logger.getLogger(SoaApplicationTest.class.getName());

    @BeforeAll
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("testPU");
        em = emf.createEntityManager();
    }

    @AfterAll
    public static void tearDown() {
        em.close();
        emf.close();
    }

    private boolean tableExists(String tableName) {
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = :t";
        Query query = em.createNativeQuery(sql);
        query.setParameter("t", tableName);
        Number count = (Number) query.getSingleResult();
        return count.intValue() > 0;
    }

    @Test
    @Order(2)
    public void testTablesExist() {
        assertTrue(tableExists("persons"));
        assertTrue(tableExists("locations"));
        assertTrue(tableExists("coordinates"));
    }

    @Test
    @Order(1)
    public void testConnection() {
        assertNotNull(em);
        assertTrue(em.isOpen());
    }

    @Test
    @Order(3)
    public void testLocalRoundtrip() {
        Person person = makePerson();

        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();

        Person retrievedPerson = em.find(Person.class, person.getId());

        assertNotNull(retrievedPerson);
        assertEquals(person.getName(), retrievedPerson.getName());
        assertEquals(person.getHeight(), retrievedPerson.getHeight());
        assertEquals(person.getBirthday(), retrievedPerson.getBirthday());
        assertEquals(person.getWeight(), retrievedPerson.getWeight());
        assertEquals(person.getEyeColor(), retrievedPerson.getEyeColor());
//        assertEquals(person.getCoordinates().getX(), retrievedPerson.getCoordinates().getX());
//        assertEquals(person.getCoordinates().getY(), retrievedPerson.getCoordinates().getY());
        assertEquals(person.getLocation().getX(), retrievedPerson.getLocation().getX());
        assertEquals(person.getLocation().getY(), retrievedPerson.getLocation().getY());
        assertEquals(person.getLocation().getZ(), retrievedPerson.getLocation().getZ());
        assertEquals(person.getLocation().getName(), retrievedPerson.getLocation().getName());
    }

    // @Test
    // public void testServerRoundtrip() {
    //     Client client = ClientBuilder.newClient();
    //     Person txPerson = makePerson();

    //     final String API_URL = "http://localhost:8080/soa-1.0-SNAPSHOT/api";
    //     Response txResp = client.target(API_URL)
    //         .path("/persons")
    //         .request(MediaType.APPLICATION_JSON)
    //         .post(Entity.entity(txPerson, MediaType.APPLICATION_JSON));
    //     assertEquals(Response.Status.CREATED.getStatusCode(), txResp.getStatus());

    //     URI rxUri = txResp.getLocation();
    //     assertNotNull(rxUri);

    //     log.info(String.valueOf(rxUri));

    //     Response rxResp = client.target(rxUri)
    //         .request(MediaType.APPLICATION_JSON)
    //         .get();
    //     assertEquals(Response.Status.OK.getStatusCode(), rxResp.getStatus());
    //     Person rxPerson = rxResp.readEntity(Person.class);
    //     assertNotNull(rxPerson);
    // }

    private static Person makePerson() {
        Person person = new Person();
        person.setName("John Doe");
        person.setHeight(180.5f);
        person.setBirthday(new Date());
        person.setWeight(75.0f);
        person.setEyeColor(Color.GREEN);

        Coordinates coordinates = new Coordinates();
        coordinates.setX(40.7128);
        coordinates.setY(100);

        Location location = new Location();
        location.setX(100.0);
        location.setY(200);
        location.setZ(300);
        location.setName("Empire State Building");

//        person.setCoordinates(coordinates);
        person.setLocation(location);
        return person;
    }

}
