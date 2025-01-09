package ru.itmo.soa3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.itmo.soa3.model.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT COUNT(p) FROM Person p WHERE p.location.x * p.location.y * p.location.z > :threshold")
    Long countByLocationThreshold(Double threshold);

    List<Person> findByName(String name);

    List<Person> findByHeightGreaterThanAndEyeColor(String height, String eyeColor);
}
