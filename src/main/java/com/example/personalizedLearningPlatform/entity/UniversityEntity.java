package com.example.personalizedLearningPlatform.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityEntity {

    private Integer id;
    private String name;
    private String location;
    private String website;
    private Integer rank;
    private String admissionRequirements;


}
