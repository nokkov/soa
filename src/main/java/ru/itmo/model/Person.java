package ru.itmo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; // Поле не может быть null, Строка не может быть пустой

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates; // Поле не может быть null

    @Column(updatable = false)
    private ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Float height; // Поле не может быть null, Значение поля должно быть больше 0

    private Date birthday; // Поле не может быть null

    private Float weight; // Поле может быть null, Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    private Color hairColor; // Поле не может быть null

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location; // Поле не может быть null

    @PrePersist
    protected void onCreate() {
        creationDate = ZonedDateTime.now();
    }

    // Getters and setters
}

