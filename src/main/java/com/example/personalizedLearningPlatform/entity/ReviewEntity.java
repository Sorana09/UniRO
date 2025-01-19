package com.example.personalizedLearningPlatform.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReviewEntity {
    private Integer id;
    private String message;
    private Integer userId;
    private Integer universityId;
    private OffsetDateTime wroteAt;
}
