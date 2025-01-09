package ru.itmo.soa3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/**")
public class CatchAllFallbackController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT})
    public void handleAll() {
        throwTheException();
    }

    private void throwTheException() {
        throw new FallbackException();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class FallbackException extends RuntimeException {
        public FallbackException() {
            super("Resource not found");
        }
    }
}
