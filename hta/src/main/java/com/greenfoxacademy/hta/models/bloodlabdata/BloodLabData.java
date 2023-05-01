package com.greenfoxacademy.hta.models.bloodlabdata;

import com.greenfoxacademy.hta.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BloodLabData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "bloodLabData")
    private User user;
    @OneToMany(mappedBy = "bloodLabData")
    private List<Glucose> glucoseValues;
    @OneToMany(mappedBy = "bloodLabData")
    private List<Calcium> calciumValues;
    @OneToMany(mappedBy = "bloodLabData")
    private List<TriGlycerides> triGlyceridesValues;
}
