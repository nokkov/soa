package ru.itmo.service2;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Getter
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorPayload {
    @XmlElement
    String message;

    @XmlElement
    String time;

    public ErrorPayload() {
    }

    public ErrorPayload(String message) {
        this.message = message;
        this.time = ZonedDateTime.now().format(FORMATTER);
    }

    public static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd'T'hh:mm:ss'Z'")
        .toFormatter();
}
