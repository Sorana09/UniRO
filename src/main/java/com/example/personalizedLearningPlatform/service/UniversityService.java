package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.UniversityRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UniversityService {


    private final UniversityRepository universityRepository;

    public List<UniversityEntity> getAllUniversities() {
        return universityRepository.findAll();
    }

    public List<UniversityEntity> getUniversitiesAllParams(Map<String, Object> filters){
        return universityRepository.findAllParams(filters);
    }

    public Optional<UniversityEntity> getUniversityById(Integer id) {
        return universityRepository.findById(id);
    }


    public UniversityEntity save(UniversityEntity universityEntity) {
        return universityRepository.save(universityEntity);
    }


    public void deleteUniversity(Integer id) {
        universityRepository.deleteById(id);
    }
}

