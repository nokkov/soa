package ru.itmo.response;

import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

public class BadParamsPayload extends ErrorPayload {
    @XmlElement
    final List<String> param;

    public BadParamsPayload(String message, List<String> param) {
        super(message);
        this.param = param;
    }

    public BadParamsPayload() {
        this("", List.of());
    }
}
