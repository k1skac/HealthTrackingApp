package com.greenfoxacademy.hta.models.medication;

public enum Units {
    MG("mg"),
    ML("ml"),
    PILL("pill"),
    DROP("drop"),
    PUFF("puff"),
    OTHER("other");

    public final String name;

    Units(String name) {
        this.name = name;
    }
}
