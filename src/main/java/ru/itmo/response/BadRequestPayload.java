package ru.itmo.response;

import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class BadRequestPayload {
    private final String message;
    private final List<String> param;
    private final String time;

    public BadRequestPayload(String message, List<String> param) {
        this.message = message;
        this.param = param;
        this.time = ZonedDateTime.now().toString();
    }
}
