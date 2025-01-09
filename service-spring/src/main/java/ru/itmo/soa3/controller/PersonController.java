package ru.itmo.soa3.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.soa3.exception.BadRequestException;
import ru.itmo.soa3.model.Person;
import ru.itmo.soa3.response.PersonListPayload;
import ru.itmo.soa3.repository.PersonRepository;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private EntityManager em;

    @Autowired
    private PersonRepository personRepository;

    // Можно менять между JSON и XML (например, через Spring настройку)
    private static final String MEDIA_TYPE = "application/xml";

    @PostMapping(consumes = MEDIA_TYPE, produces = MEDIA_TYPE)
    public ResponseEntity<Void> add(@Valid @RequestBody Person person) {
        personRepository.save(person);
        URI location = URI.create(String.format("/persons/%d", person.getId()));
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/{id}", produces = MEDIA_TYPE)
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping(value = "/{id}", consumes = MEDIA_TYPE, produces = MEDIA_TYPE)
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person updatedPerson) {
        return personRepository.findById(id)
                .map(existingPerson -> {
                    existingPerson.setName(updatedPerson.getName());
                    existingPerson.setHeight(updatedPerson.getHeight());
                    existingPerson.setBirthday(updatedPerson.getBirthday());
                    existingPerson.setWeight(updatedPerson.getWeight());
                    existingPerson.setEyeColor(updatedPerson.getEyeColor());
                    existingPerson.setLocation(updatedPerson.getLocation());
                    personRepository.save(existingPerson);
                    return ResponseEntity.ok(existingPerson);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.delete(person);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping(value = "/count-by-location", produces = MEDIA_TYPE)
    public ResponseEntity<Long> countByLocation(@RequestParam Double threshold) {
        Long count = em.createQuery(
                "SELECT COUNT(p) FROM Person p WHERE p.location.x * p.location.y * p.location.z > :threshold", Long.class)
                .setParameter("threshold", threshold)
                .getSingleResult();
        return ResponseEntity.ok(count);
    }

    @GetMapping(produces = MEDIA_TYPE)
    public ResponseEntity<PersonListPayload> getPersons(
            @RequestParam(value = "sort", required = false) List<String> sortBy,
            @RequestParam(value = "filter", required = false) List<String> filters,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) throws BadRequestException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);

        try {
            List<Order> orders = new ArrayList<>();
            if (sortBy != null) {
                for (String sortSpec : sortBy) {
                    if (sortSpec.isEmpty()) {
                        continue;
                    }
                    if (sortSpec.startsWith("-")) {
                        orders.add(cb.desc(root.get(sortSpec.substring(1))));
                    } else {
                        orders.add(cb.asc(root.get(sortSpec)));
                    }
                }
                if (!orders.isEmpty()) {
                    cq.orderBy(orders);
                }
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Bad sort spec", "sort");
        }

        if (filters != null) {
            for (String filterSpec : filters) {
                if (filterSpec.isEmpty()) {
                    continue;
                }
                String[] split = filterSpec.split("[ +]");
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
        }

        int from = page * pageSize;
        int upto = from + pageSize;

        Query query = em.createQuery(cq)
                .setFirstResult(from)
                .setMaxResults(upto - from);
        List<Person> persons = query.getResultList();
        long total = (Long) em.createQuery("SELECT COUNT(id) FROM Person").getSingleResult();

        PersonListPayload result = new PersonListPayload();
        result.setFrom(from);
        result.setUpto(upto);
        result.setTotal(total);
        result.setPersons(persons);
        return ResponseEntity.ok(result);
    }
}

