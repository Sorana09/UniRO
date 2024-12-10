package com.example.personalizedLearningPlatform.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    private Integer id;
    private String name;
    private Integer universityId;
}
