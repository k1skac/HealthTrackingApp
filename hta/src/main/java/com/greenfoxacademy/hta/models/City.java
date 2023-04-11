package com.greenfoxacademy.hta.models;

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
  public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cityName;

    /**
    * Geo coordinate conversion example:
    * 47° 13,893'
    * 47 degrees + (13.893 minutes / 60) = 47.23155 degrees
    *
    *"y coordinate:"
    */
    private double yLatitude;

    /**
    /*"x coordinate:"
     */
    private double xLongitude;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    List<User> users = new ArrayList<>();
  }
