package com.example.HandlingKafkaErrors.kafka;

import com.example.HandlingKafkaErrors.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonKafkaConsumer {
    @KafkaListener(topics = "#{'${kafka.person.topic}'.split(',')}",
            containerFactory = "personKafkaListenerContainerFactory"
    )
    public void processPerson(Person person) {
        log.info("Person processed: {}", person.toString());
    }

}
