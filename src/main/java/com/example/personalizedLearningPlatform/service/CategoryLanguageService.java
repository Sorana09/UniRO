package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import com.example.personalizedLearningPlatform.repo.CategoryLanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CategoryLanguageService {
    private final CategoryLanguageRepository categoryLanguageRepository;
    public void saveCategoryWithLanguages(List<CategoryEntity> categoryEntities,
                                               Map<String, List<LanguageEntity>> categoryLanguagesMap) {
        for (CategoryEntity category : categoryEntities) {
            Integer categoryId = categoryLanguageRepository.findOrInserCategory(category);

            List<LanguageEntity> languageEntities = categoryLanguagesMap.get(category.getName());
            if (languageEntities != null) {
                for (LanguageEntity languageEntity : languageEntities) {
                    Integer languageId = categoryLanguageRepository.findOrInsertLanguage(languageEntity);
                    categoryLanguageRepository.linkCategoryWithLanguage(categoryId, languageId);
                }
            }
        }
    }
}
