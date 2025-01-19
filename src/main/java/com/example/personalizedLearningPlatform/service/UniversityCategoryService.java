package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.UniversityCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UniversityCategoryService {

    private final UniversityCategoryRepository universityRepository;

    public void saveUniversitiesWithCategories(List<UniversityEntity> universities,
                                               Map<String, List<CategoryEntity>> universityCategoryMap) {
        for (UniversityEntity university : universities) {
            Integer universityId = universityRepository.findOrInsertUniversity(university);

            List<CategoryEntity> categories = universityCategoryMap.get(university.getName());
            if (categories != null) {
                for (CategoryEntity category : categories) {
                    Integer categoryId = universityRepository.findOrInsertCategory(category);
                    universityRepository.linkUniversityWithCategory(universityId, categoryId);
                }
            }
        }
    }
}
