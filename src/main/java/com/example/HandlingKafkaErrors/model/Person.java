package com.example.HandlingKafkaErrors.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    private String uuid;

    private String firstName;

    private String lastName;

    private int age;

    private Loc loc;

}
