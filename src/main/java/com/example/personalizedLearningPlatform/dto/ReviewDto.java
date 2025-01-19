package com.example.personalizedLearningPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReviewDto {

    private Integer id;
    private String message;
    private Integer userId;
    private Integer universityId;
    private OffsetDateTime wroteAt;
}
