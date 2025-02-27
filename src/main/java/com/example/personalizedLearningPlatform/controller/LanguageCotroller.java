package com.example.personalizedLearningPlatform.controller;

import com.example.personalizedLearningPlatform.dto.LanguageDto;
import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import com.example.personalizedLearningPlatform.service.LanguageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.personalizedLearningPlatform.dto.mapper.Mapper.mapper;

@RestController
@RequestMapping("/languages")
@AllArgsConstructor
public class LanguageCotroller {
    private final LanguageService languageService;

    @GetMapping
    public List<LanguageDto> getAllLanguages() {
        return languageService.getAllLanguages().stream()
                .map(languageEntity -> mapper(languageEntity))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> getLanguageById(@PathVariable Integer id) {
        if(languageService.getById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper(languageService.getById(id).get()));
    }
    @GetMapping("/{id}/cat")
    public List<CategoryEntity> getCategoriesByLanguageId(@PathVariable Integer id) {
        return languageService.getCategoriesByLanguageId(id);
    }
    @PostMapping
    public ResponseEntity<LanguageDto> createLanguage(@RequestBody LanguageEntity languageEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper(languageService.saveLanguage(languageEntity)));

    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLanguageById(@PathVariable Integer id) {
        if(languageService.getById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        languageService.deleteLanguageById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
