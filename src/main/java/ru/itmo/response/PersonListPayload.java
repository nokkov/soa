package ru.itmo.response;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.model.Person;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "persons")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonListPayload {
    @XmlElement(name = "from")
    long from = 0;

    @XmlElement(name = "upto")
    long upto = 0;

    @XmlElement(name = "total")
    long total = 0;

    @XmlElement(name = "persons")
    List<Person> persons = null;

    public PersonListPayload() {}
}
