package ru.itmo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "locations")
@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlElement
    private Double x; // Поле не может быть null

    @XmlElement
    private Integer y; // Поле не может быть null

    @XmlElement
    private Integer z; // Поле не может быть null

    @Size(max = 255)
    @XmlElement
    private String name; // Строка не может быть пустой, Поле не может быть null
}