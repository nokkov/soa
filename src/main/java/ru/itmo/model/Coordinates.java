package ru.itmo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coordinates")
@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(-371)
    @XmlElement
    private double x; // Значение поля должно быть больше -371

    @Min(0)
    @XmlElement
    private long y; // Значение поля должно быть больше 0
}
