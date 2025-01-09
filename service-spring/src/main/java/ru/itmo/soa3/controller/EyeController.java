package ru.itmo.soa3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.soa3.model.Color;

@RestController
@RequestMapping("/api/eye-color")
public class EyeController {

    @GetMapping(produces = "application/xml")
    public ResponseEntity<String> getEyeColors() {
        StringBuilder result = new StringBuilder("<colors>");
        for (Color color : Color.values()) {
            result.append("<color>").append(color.name()).append("</color>");
        }
        result.append("</colors>");
        return ResponseEntity.ok(result.toString());
    }
}

