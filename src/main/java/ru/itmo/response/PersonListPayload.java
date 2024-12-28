package ru.itmo.response;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.model.Person;

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

    @XmlElementWrapper(name="persons")
    @XmlElement(name = "person")
    List<Person> persons = null;

    public PersonListPayload() {}
}
