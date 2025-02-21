package com.example.personalizedLearningPlatform.dto;

import com.example.personalizedLearningPlatform.dto.enums.AdmmisionRequirementsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<CategoryDto> categories;
    private String admissionRequirements;

}
