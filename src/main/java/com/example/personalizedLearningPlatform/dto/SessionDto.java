package com.example.personalizedLearningPlatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    private Integer id;
    private OffsetDateTime expiredAt;
    private String sessionKey;
    private Integer userId;
}
