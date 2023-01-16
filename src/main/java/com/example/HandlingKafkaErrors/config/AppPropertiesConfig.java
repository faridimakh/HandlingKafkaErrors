package com.example.HandlingKafkaErrors.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Component
@Configuration
public class AppPropertiesConfig {
    @Value("${kafka.person.bootstrapAddress}")
    private String kafkaServer;

    @Value("${kafka.person.groupId}")
    private String kafkaConsumerGroupId;

    @Value("${kafka.person.topic}")
    private String kafkaTopicAccessory;
}
