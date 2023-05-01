package com.greenfoxacademy.hta.models.log;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LogType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String message;

    @OneToMany(mappedBy = "logType")
    private List<Log> log;

    public LogType(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
