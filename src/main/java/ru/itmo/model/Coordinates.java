package ru.itmo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coordinates")
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double x; // Значение поля должно быть больше -371

    private long y; // Значение поля должно быть больше 0

    // Getters and setters
}
