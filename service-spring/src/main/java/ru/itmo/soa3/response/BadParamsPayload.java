package ru.itmo.soa3.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import ru.itmo.soa3.config.DateFormatXmlAdapter;

import java.time.ZonedDateTime;
import java.util.List;

@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class BadParamsPayload {
    @XmlElement
    final String message;
    @XmlElement
    final String time;
    @XmlElement
    final List<String> param;

    public BadParamsPayload() {
        this("", List.of());
    }

    public BadParamsPayload(String message, List<String> param) {
        this.message = message;
        this.param = param;
        this.time = ZonedDateTime.now().format(DateFormatXmlAdapter.FORMATTER);
    }
}
