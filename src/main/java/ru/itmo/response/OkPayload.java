package ru.itmo.response;

import lombok.Getter;
import lombok.Setter;
import ru.itmo.model.Person;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "persons")
@XmlAccessorType(XmlAccessType.FIELD)
public class OkPayload {
    @XmlElement(name = "from")
    int from;

    @XmlElement(name = "upto")
    int upto;

    @XmlElement(name = "total")
    int total;

    @XmlElement(name = "persons")
    List<Person> persons;
}
