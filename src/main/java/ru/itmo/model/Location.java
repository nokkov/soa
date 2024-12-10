package ru.itmo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double x; // Поле не может быть null

    private Integer y; // Поле не может быть null

    private Integer z; // Поле не может быть null

    private String name; // Строка не может быть пустой, Поле не может быть null

    // Getters and setters
}

