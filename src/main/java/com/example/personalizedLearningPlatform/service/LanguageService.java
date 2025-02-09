package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import com.example.personalizedLearningPlatform.repo.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public List<LanguageEntity> getAllLanguages() {
        return languageRepository.findAll();
    }
    public Optional<LanguageEntity> getById(Integer id) {
        return languageRepository.findById(id);
    }
    public LanguageEntity getLanguageByName(String name) {
        return languageRepository.findByName(name);
    }
    public LanguageEntity saveLanguage(LanguageEntity language) {
        return languageRepository.save(language);
    }
    public List<LanguageEntity> getByCategoryId(Integer categoryId) {
        return languageRepository.findByCategoryId(categoryId);
    }
    public void deleteLanguageById(Integer id) {
        languageRepository.deleteById(id);
    }
}
