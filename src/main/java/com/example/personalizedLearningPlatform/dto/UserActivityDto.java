package com.example.personalizedLearningPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityDto {
    private Integer id;
    private Integer userId;
    private String action;
    private String endpoint;
    private OffsetDateTime timestamp;
}
