package ru.itmo.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends Exception {
    private final String message;
    private final String param;

    public BadRequestException(String message, String param) {
        this.message = message;
        this.param = param;
    }
}
