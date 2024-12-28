package ru.itmo.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorPayload {
    @XmlElement
    final String message;
    @XmlElement
    final String time;

    public ErrorPayload(String message) {
        this.message = message;
        this.time = ZonedDateTime.now().toString();
    }

    public ErrorPayload() {
        this("");
    }
}
