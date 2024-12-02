package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.UniversityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    public List<UniversityEntity> getAllUniversities() {
        return universityRepository.findAll();
    }

    public Optional<UniversityEntity> getUniversityById(int id) {
        return universityRepository.findById(id);
    }


    public UniversityEntity save(UniversityEntity universityEntity) {
        return universityRepository.saveAndFlush(universityEntity);
    }

    public void deleteUniversity(int id) {
        universityRepository.deleteById(id);
    }
}

