package com.example.personalizedLearningPlatform.controller;

import com.example.personalizedLearningPlatform.dto.CategoryDto;
import com.example.personalizedLearningPlatform.dto.mapper.ExcelToEntityMapper;
import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import com.example.personalizedLearningPlatform.entity.StudyProgramEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.service.CategoryLanguageService;
import com.example.personalizedLearningPlatform.service.CategoryService;
import com.example.personalizedLearningPlatform.service.CategoryStudyProgramService;
import com.example.personalizedLearningPlatform.service.OpenAIService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.personalizedLearningPlatform.dto.mapper.Mapper.mapper;


@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryLanguageService categoryLanguageService;
    private final CategoryStudyProgramService categoryStudyProgramService;
    private final OpenAIService openAIService;


    @GetMapping
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryEntity> getCategoryById(@PathVariable Integer id) {
        Optional<CategoryEntity> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PostMapping("/upload-languages")
    public ResponseEntity<String> uploadLanguages(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("categories_languages", ".xlsx");
            file.transferTo(tempFile);

            ExcelToEntityMapper mapper = new ExcelToEntityMapper();

            Map<String, List<CategoryEntity>> categoryEntityMap = mapper.readFacultySheet(tempFile.getAbsolutePath());
            Map<String, List<LanguageEntity>> categoryLanguageMap = mapper.readCategoryLanguagesSheet(tempFile.getAbsolutePath());
            Map<String, List<StudyProgramEntity>> categoryStudyDomainMap = mapper.readCategoryStudyDomainSheet(tempFile.getAbsolutePath());

            List<CategoryEntity> categoryEntities = new ArrayList<>();
            for (List<CategoryEntity> categories : categoryEntityMap.values()) {
                categoryEntities.addAll(categories);
            }

            categoryStudyProgramService.saveCategoryWithStudyProgram(categoryEntities, categoryStudyDomainMap);
            categoryLanguageService.saveCategoryWithLanguages(categoryEntities, categoryLanguageMap);

            tempFile.delete();
            return ResponseEntity.ok("Categories, languages, and study domains uploaded and saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload and process the file.");
        }
    }


    @GetMapping("/uni/{id}")
    public List<CategoryDto> getCategoryByUniversityId(@PathVariable Integer id) {
        List<CategoryEntity> category = categoryService.findByUniversityId(id);
        return category.stream()
                .map(it -> mapper(it))
                .toList();
    }

    @GetMapping("/universities/{categoryId}")
    public List<UniversityEntity> getUniversitiesByCategory(@PathVariable Integer categoryId) {
        return categoryService.getUniversitiesByCategory(categoryId);
    }

    @PostMapping
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity categoryEntity) {
        CategoryEntity createdCategory = categoryService.createOrUpdateCategory(categoryEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity categoryEntity) {
        if (!categoryService.getCategoryById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categoryEntity.setId(id);
        CategoryEntity updatedCategory = categoryService.createOrUpdateCategory(categoryEntity);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/{id}/description")
    public String setDescription(@PathVariable Integer id) throws IOException {
        CategoryEntity category =categoryService.getCategoryById(id).get();
        String generatedDescription = openAIService.generateInformationForCategories(category.getName());
       // categoryService.updateDescription(id, generatedDescription);
        return generatedDescription;
    }

    @PutMapping("/{id}/setdescription")
    public ResponseEntity<CategoryEntity> setDescription(@PathVariable Integer id, @RequestBody String description) {
        if (!categoryService.getCategoryById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categoryService.updateDescription(id, description);
        return ResponseEntity.ok(categoryService.getCategoryById(id).get());
    }

    @GetMapping("/{id}/coordinates")
    public ResponseEntity<double[]> generateCoordinates(@PathVariable Integer id) throws IOException {
        CategoryEntity category =categoryService.getCategoryById(id).get();
        double[] coordinates = openAIService.generateInformationForCategoriesLatAndLong(category.getName());
        categoryService.updateCoordinates(id, coordinates[0], coordinates[1]);
        return new ResponseEntity<>(coordinates, HttpStatus.OK);
    }

    @GetMapping("/{id}/setEntranceMethod")
    public ResponseEntity<CategoryEntity> setEntranceMethod(@PathVariable Integer id) throws IOException {
        if (!categoryService.getCategoryById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String entranceMethod = openAIService.generateEntranceMethod(categoryService.getCategoryById(id).get().getName());
        categoryService.updateEntranceMethod(id, entranceMethod);
        return ResponseEntity.ok(categoryService.getCategoryById(id).get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        if (!categoryService.getCategoryById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}



