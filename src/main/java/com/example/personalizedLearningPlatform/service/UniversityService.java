package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.CategoryRepository;
import com.example.personalizedLearningPlatform.repo.UniversityCategoryRepository;
import com.example.personalizedLearningPlatform.repo.UniversityRepoExtract;
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
    private final CategoryRepository categoryRepository;
    private final UniversityCategoryRepository universityCategoryRepository;

    private final UniversityRepoExtract universityRepoExtract;

    public UniversityEntity getUniversityByName(String name) {
        return universityRepoExtract.findByName(name).get();
    }

    public void saveAllUniversities(List<UniversityEntity> universities) {
        universityRepoExtract.saveAllUniversities(universities);
    }

    public List<UniversityEntity> getAllUniversities() {
        return universityRepository.findAll();
    }

    public List<UniversityEntity> getUniversitiesAllParams(Map<String, Object> filters) {
        return universityRepository.findAllParams(filters);
    }

    public Optional<UniversityEntity> getUniversityById(Integer id) {
        Optional<UniversityEntity> university = Optional.of(universityRepository.findById(id));
        if (university.isPresent()) {
            return university;
        }
        return Optional.empty();
    }


    public UniversityEntity save(UniversityEntity universityEntity) {
        return universityRepository.save(universityEntity);

    }


    public void deleteUniversity(Integer id) {
        universityRepository.delete(id);
    }

    public List<CategoryEntity> getCategoriesByUniversityId(Integer universityId) {
        return universityRepository.getCategoriesByUniversityId(universityId);
    }
}

