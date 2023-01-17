package com.example.HandlingKafkaErrors.kaka;

import com.example.HandlingKafkaErrors.kaka.conf.Applicationproperties;
import com.example.HandlingKafkaErrors.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class PersonKafkaProducer {
    @Value("${kafka.person.topics.input}")
    private String topic;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonKafkaProducer.class);

    private final KafkaTemplate<String, Person> kafkaTemplate;

    public PersonKafkaProducer(Applicationproperties applicationproperties, KafkaTemplate<String, Person> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Person data) {

        LOGGER.info(String.format("Message sent -> %s", data));

        Message<Person> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, data.getUuid())
                .build();

        kafkaTemplate.send(message);
    }
}


//package com.example.HandlingKafkaErrors.kaka;
//
//        import com.example.HandlingKafkaErrors.model.Person;
//        import lombok.RequiredArgsConstructor;
//        import lombok.extern.slf4j.Slf4j;
//        import org.springframework.kafka.annotation.KafkaListener;
//        import org.springframework.kafka.support.KafkaHeaders;
//        import org.springframework.messaging.handler.annotation.Header;
//        import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//class PersonKafkaConsumer {
//
//    @KafkaListener(topics = "#{'${kafka.person.topics.input}'.split(',')}",
//            containerFactory = "personKafkaListenerContainerFactory")
//    public void processPerson(Person person,
//                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                              @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String msgKey) {
//        log.debug(">>> Person process srarted, idempotencyKey={}", person.getUuid());
//        if (person.isnegatifAge() || person.getLoc().isnegatifLat() || person.getLoc().isnegatifLgt()) {
//            log.error("Age can't be negative, found in Person");
//            throw new RuntimeException("Age can't be negative, found in Person=" + person);
//        }
//        log.debug("<<< Person processed: {}", person);
//    }
//}
