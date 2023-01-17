package com.example.HandlingKafkaErrors.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Loc {
    private double lat;
    private double lgt;

    public Boolean isnegatifLat() {
        return this.lat < 0;
    }

    public Boolean isnegatifLgt() {
        return this.lgt < 0;
    }
}
