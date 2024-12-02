package com.example.personalizedLearningPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDto {
    private Integer id;
    private String name;
    private String location;
    private String website;
    private Integer rank;
    private String admission_requirements;
    private List<String> domains;
}
