package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.repo.UserActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserActivityService {
    private final UserActivityRepository userActivityRepository;
}
