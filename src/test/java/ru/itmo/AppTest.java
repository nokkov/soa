package ru.itmo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.itmo.model.Color;
import ru.itmo.model.Coordinates;
import ru.itmo.model.Location;
import ru.itmo.model.Person;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

    @Test
    public void testConnection() {
        assertNotNull(em);
        assertTrue(em.isOpen());
    }

    @Test
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
}
