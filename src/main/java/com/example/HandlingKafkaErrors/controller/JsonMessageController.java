package com.example.HandlingKafkaErrors.controller;

import com.example.HandlingKafkaErrors.kaka.PersonKafkaProducer;
import com.example.HandlingKafkaErrors.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class JsonMessageController {

    private final PersonKafkaProducer personKafkaProducer;

    public JsonMessageController(PersonKafkaProducer personKafkaProducer) {
        this.personKafkaProducer = personKafkaProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody Person person) {
        personKafkaProducer.sendMessage(person);
        return ResponseEntity.ok("Json message sent to kafka topic");
    }
}
