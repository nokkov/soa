package ru.itmo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.config.DateFormatXmlAdapter;

import java.time.ZonedDateTime;
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

    @NotEmpty
    @XmlElement
    @NotNull
    @Column(columnDefinition = "TEXT")
    String name; // Поле не может быть null, Строка не может быть пустой

    @XmlElement
    //@XmlJavaTypeAdapter(DateFormatXmlAdapter.class)
    String creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(0)
    @XmlElement
    @NotNull
    Float height; // Поле не может быть null, Значение поля должно быть больше 0

    @XmlElement
    @NotNull
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    String birthday; // Поле не может быть null

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
        creationDate = ZonedDateTime.now().format(DateFormatXmlAdapter.FORMATTER);
    }

    public Person() {
    }
}

