package com.example.HandlingKafkaErrors.kaka;

import com.example.HandlingKafkaErrors.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonKafkaConsumer.class);
    @Value("${kafka.person.topics.input}")
    private String topic;

    private CountDownLatch latch = new CountDownLatch(1);

    private Person payload;
    private String key;

    @KafkaListener(topics = "${kafka.person.topics.input}",
            containerFactory = "personKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, Person> consumerRecord,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset)
    {
        this.key = consumerRecord.key();
        this.payload = consumerRecord.value();
        latch.countDown();

        LOGGER.info("Received a message contains a person information with id {}, from  topic name: {}, " +
                "partition: {}, offset :{}", consumerRecord.value().toString(), topic, partition, offset);

        if (payload.isnegatifAge()) {
            log.error("Age can't be negative, found in Person");
            throw new RuntimeException("Age can't be negative, found in Person=" + payload);
        }
        if (payload.getLoc().isnegatifLat()) {
            log.error("Latitude can't be negative, found in Person");
            throw new RuntimeException("Latitude can't be negative, found in Person=" + payload);
        }
        if ( payload.getLoc().isnegatifLgt()) {
            log.error("langitude can't be negative, found in Person");
            throw new RuntimeException("langitude can't be negative, found in Person=" + payload);
        }
    }

    public Pair<String, Person> awaitMessage() throws InterruptedException {
        boolean messageConsumed = this.latch.await(500, TimeUnit.MILLISECONDS);
        if (!messageConsumed) {
            throw new RuntimeException("message not consumed");
        } else {
            this.latch = new CountDownLatch(1);
            return new Pair<>(this.key, this.payload);
        }
    }

}

