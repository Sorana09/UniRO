package com.example.personalizedLearningPlatform.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityEntity {
    private Integer id;
    private Integer userId;
    private String action;
    private String endpoint;
    private OffsetDateTime timestamp;
}
