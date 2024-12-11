package ru.itmo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.model.Color;
import ru.itmo.model.Coordinates;
import ru.itmo.model.Location;
import ru.itmo.model.Person;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class AppTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;

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
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = :tableName";
        Query query = em.createNativeQuery(sql);
        query.setParameter("tableName", tableName.toUpperCase());
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
    public void testAddAndRetrievePerson() {
        Person person = new Person();
        person.setName("John Doe");
        person.setHeight(180.5f);
        person.setBirthday(new Date());
        person.setWeight(75.0f);
        person.setHairColor(Color.GREEN);
        
        Coordinates coordinates = new Coordinates();
        coordinates.setX(40.7128);
        coordinates.setY(100);
        
        Location location = new Location();
        location.setX(100.0);
        location.setY(200);
        location.setZ(300);
        location.setName("Empire State Building");
        
        person.setCoordinates(coordinates);
        person.setLocation(location);

        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();

        Person retrievedPerson = em.find(Person.class, person.getId());

        assertNotNull(retrievedPerson);
        assertEquals(person.getName(), retrievedPerson.getName());
        assertEquals(person.getHeight(), retrievedPerson.getHeight());
        assertEquals(person.getBirthday(), retrievedPerson.getBirthday());
        assertEquals(person.getWeight(), retrievedPerson.getWeight());
        assertEquals(person.getHairColor(), retrievedPerson.getHairColor());
        assertEquals(person.getCoordinates().getX(), retrievedPerson.getCoordinates().getX());
        assertEquals(person.getCoordinates().getY(), retrievedPerson.getCoordinates().getY());
        assertEquals(person.getLocation().getX(), retrievedPerson.getLocation().getX());
        assertEquals(person.getLocation().getY(), retrievedPerson.getLocation().getY());
        assertEquals(person.getLocation().getZ(), retrievedPerson.getLocation().getZ());
        assertEquals(person.getLocation().getName(), retrievedPerson.getLocation().getName());
    }

    // @Test
    // @Order(4)
    // public void testAddPerson() {
    //     Client client = ClientBuilder.newClient();

    //     Person person = new Person();
    //     person.setName("John Doe");
    //     person.setHeight(180.5f);
    //     person.setBirthday(new Date());
    //     person.setWeight(75.0f);
    //     person.setHairColor(Color.GREEN);
        
    //     Coordinates coordinates = new Coordinates();
    //     coordinates.setX(40.7128);
    //     coordinates.setY(100);
        
    //     Location location = new Location();
    //     location.setX(100.0);
    //     location.setY(200);
    //     location.setZ(300);
    //     location.setName("Empire State Building");
        
    //     person.setCoordinates(coordinates);
    //     person.setLocation(location);

    //     // Send POST request to add person
    //     Response response = client.target("http://localhost:8080/soa-1.0-SNAPSHOT/api")
    //                               .path("/persons")
    //                               .request(MediaType.APPLICATION_JSON)
    //                               .post(Entity.entity(person, MediaType.APPLICATION_JSON));

    //     assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());        
    // }

    // @Test
    // @Order(5)
    // public void testGetPerson() {
    //     Client client = ClientBuilder.newClient();

    //     Response response = client.target("http://localhost:8080/soa-1.0-SNAPSHOT/api")
    //                               .path("/persons/" + 1)
    //                               .request(MediaType.APPLICATION_JSON)
    //                               .get();

    //     assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    //     Person retrievedPerson = response.readEntity(Person.class);
    //     assertNotNull(retrievedPerson);
    //     // assertEquals("John Doe", retrievedPerson.getName());
    //     assertEquals(180.5f, retrievedPerson.getHeight());
    //     assertEquals(75.0f, retrievedPerson.getWeight());
    //     assertEquals(Color.GREEN, retrievedPerson.getHairColor());
    //     assertEquals(40.7128, retrievedPerson.getCoordinates().getX());
    //     assertEquals(100, retrievedPerson.getCoordinates().getY());
    //     assertEquals(100.0, retrievedPerson.getLocation().getX());
    //     assertEquals(200, retrievedPerson.getLocation().getY());
    //     assertEquals(300, retrievedPerson.getLocation().getZ());
    //     assertEquals("Empire State Building", retrievedPerson.getLocation().getName());
    // }
}
