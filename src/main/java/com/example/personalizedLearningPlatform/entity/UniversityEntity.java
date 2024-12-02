package com.example.personalizedLearningPlatform.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "university_entity")
public class UniversityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(nullable = false, length = 255)
    private String name;

    @NotNull
    @Column(nullable = false, length = 255)
    private String location;

    @NotNull
    @Column(nullable = false, length = 255)
    private String website;

    @NotNull
    @Column(nullable = false)
    private int rank;

    @NotNull
    @Column(length = 1000)
    private String admission_requirements;


}
