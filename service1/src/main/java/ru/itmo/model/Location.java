package ru.itmo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.*;
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
    @XmlTransient
    private Long id;

    @XmlElement
    @NotNull
    private Double x; // Поле не может быть null

    @XmlElement
    @NotNull
    private Integer y; // Поле не может быть null

    @XmlElement
    @NotNull
    private Integer z; // Поле не может быть null

    @Size(max = 255)
    @XmlElement
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String name; // Строка не может быть пустой, Поле не может быть null
}