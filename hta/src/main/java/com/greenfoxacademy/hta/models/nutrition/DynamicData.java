package com.greenfoxacademy.hta.models.nutrition;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    int dynamicInt;
    public DynamicData(int dynamicInt){
        this.dynamicInt = dynamicInt;
    }
}