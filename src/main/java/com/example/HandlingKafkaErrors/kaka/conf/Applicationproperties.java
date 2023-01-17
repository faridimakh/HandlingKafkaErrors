package com.example.HandlingKafkaErrors.kaka.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
@Configuration
public class Applicationproperties {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${kafka.person.groupId}")
    private String kafkaConsumerGroupId;

    @Value("${kafka.person.topics.input}")
    private String kafkaTopicAccessory;

    @Value("${kafka.person.topics.dlq}")
    private String kafkaTopicAccessoryDlq;
}
