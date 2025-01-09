package ru.itmo.soa3.config;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Configuration
public class DateFormatXmlAdapter extends XmlAdapter<String, ZonedDateTime> {

    public static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd'T'hh:mm:ss'Z'")
        .toFormatter();

    @Override
    public ZonedDateTime unmarshal(String s) throws Exception {
        return FORMATTER.parse(s, ZonedDateTime::from);
    }

    @Override
    public String marshal(ZonedDateTime zonedDateTime) throws Exception {
        return FORMATTER.format(zonedDateTime);
    }
}

