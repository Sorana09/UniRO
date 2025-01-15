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
public class SessionEntity {
    private Integer id;
    private OffsetDateTime expiredAt;
    private String sessionKey;
    private Integer userId;
}
