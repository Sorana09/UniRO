package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.repo.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
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

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<CategoryEntity> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
