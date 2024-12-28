package ru.itmo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Logger;

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
    @NotNull
    String name; // Поле не может быть null, Строка не может быть пустой

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "coordinates_id")
//    @XmlElement
//    @NotNull
//    Coordinates coordinates; // Поле не может быть null

    @XmlElement
    ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(0)
    @XmlElement
    @NotNull
    Float height; // Поле не может быть null, Значение поля должно быть больше 0

    @XmlElement
    @NotNull
    Date birthday; // Поле не может быть null

    @Min(0)
    @XmlElement
    @NotNull
    Float weight; // Поле может быть null, Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    @NotNull
    Color eyeColor; // Поле не может быть null

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    @XmlElement
    Location location; // Поле не может быть null

    private static Logger log = Logger.getLogger(Person.class.getName());

    @PrePersist
    protected void onCreate() {
        log.info("onCreate person");
        creationDate = ZonedDateTime.now();
    }

    public Person() {
    }
}

