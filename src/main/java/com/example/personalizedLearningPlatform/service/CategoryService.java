package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import com.example.personalizedLearningPlatform.entity.StudyProgramEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryEntity createOrUpdateCategory(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }

    public List<CategoryEntity> findByUniversityId(Integer universityId) {
        return categoryRepository.findByUniversityId(universityId);
    }

    public void updateDescription(Integer id, String description) {
        categoryRepository.updateDescription(id, description);
    }

    public void updateEntranceMethod(Integer id, String entranceMethod) {
        categoryRepository.updateEntranceMethod(id, entranceMethod);
    }

    public void updateCoordinates(Integer id, Double latitude, Double longitude) {
        categoryRepository.updateCoordinates(id, latitude, longitude);
    }

    public List<LanguageEntity> getLanguageCategories(Integer categoryId) {
        return categoryRepository.getLanguageByCategoryId(categoryId);
    }

    public List<StudyProgramEntity> getStudyProgramCategories(Integer categoryId) {
        return categoryRepository.getStudyProgramByCategoryId(categoryId);
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<CategoryEntity> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    public List<UniversityEntity> getUniversitiesByCategory(Integer categoryId) {
        return categoryRepository.getUniversitiesByCategory(categoryId);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
