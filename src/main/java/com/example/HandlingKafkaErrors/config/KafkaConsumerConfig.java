package com.example.HandlingKafkaErrors.config;

import com.example.HandlingKafkaErrors.config.kafkaerror.KafkaErrorHandler;
import com.example.HandlingKafkaErrors.model.Person;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ConditionalOnProperty(name = "kafka.consumer.person.enabled", matchIfMissing = true)
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final AppPropertiesConfig appPropertiesConfig;

    /**
     * Kafka consumer factory setup - standard factory.
     *
     * @return JSON factory.
     */
    @Bean
    public ConsumerFactory<String, Person> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, appPropertiesConfig.getKafkaServer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, appPropertiesConfig.getKafkaConsumerGroupId());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

        // key.deserializer
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // value.deserializer
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // spring.deserializer.key.delegate.class
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);

        ErrorHandlingDeserializer<Person> errorHandlingDeserializer
                = new ErrorHandlingDeserializer<>(new JsonDeserializer<>(Person.class));

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                errorHandlingDeserializer);
    }

    /**
     * Kafka consumer factory setup - wrapper for concurrency.
     *
     * @return wrapped factory.
     */
    @Bean("personKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Person>
    promoMaterialsKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Person> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setErrorHandler(new KafkaErrorHandler());
        return factory;
    }
}
