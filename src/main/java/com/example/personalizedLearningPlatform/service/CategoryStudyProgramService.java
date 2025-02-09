package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import com.example.personalizedLearningPlatform.entity.StudyProgramEntity;
import com.example.personalizedLearningPlatform.repo.CategoryStudyProgramRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CategoryStudyProgramService {
    private final CategoryStudyProgramRepository categoryStudyProgramRepository;
    public void saveCategoryWithStudyProgram(List<CategoryEntity> categoryEntities,
                                          Map<String, List<StudyProgramEntity>> categoryStudyProgramMap) {
        for (CategoryEntity category : categoryEntities) {
            Integer categoryId = categoryStudyProgramRepository.findOrInserCategory(category);

            List<StudyProgramEntity> studyProgramEntities = categoryStudyProgramMap.get(category.getName());
            if (studyProgramEntities != null) {
                for (StudyProgramEntity studyProgramEntity : studyProgramEntities) {
                    Integer studyId = categoryStudyProgramRepository.findOrInsertStudy(studyProgramEntity);
                    categoryStudyProgramRepository.linkCategoryWithStudyProgram(categoryId, studyId);
                }
            }
        }
    }
}
