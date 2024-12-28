package ru.itmo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "persons")
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement
    long id; // Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Size(max = 255)
    @NotEmpty
    @XmlElement
    String name; // Поле не может быть null, Строка не может быть пустой

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id")
    @XmlElement
    Coordinates coordinates; // Поле не может быть null

    @Column(updatable = false)
    @XmlElement
    ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(0)
    @XmlElement
    float height; // Поле не может быть null, Значение поля должно быть больше 0

    @XmlElement
    Date birthday; // Поле не может быть null

    @Min(0)
    @XmlElement
    float weight; // Поле может быть null, Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    @XmlElement
    Color hairColor; // Поле не может быть null

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    @XmlElement
    Location location; // Поле не может быть null

    @PrePersist
    protected void onCreate() {
        creationDate = ZonedDateTime.now();
    }

    public Person() {
    }
}

