package com.example.personalizedLearningPlatform.controller;

import com.example.personalizedLearningPlatform.dto.UniversityDto;
import com.example.personalizedLearningPlatform.dto.mapper.ExcelToUniversityMapper;
import com.example.personalizedLearningPlatform.dto.mapper.Mapper;
import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.service.UniversityCategoryService;
import com.example.personalizedLearningPlatform.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.personalizedLearningPlatform.dto.mapper.Mapper.*;


@RestController
@RequestMapping("/universities")
public class UniversityController {

    @Autowired
    private UniversityService universityService;
    @Autowired
    private UniversityCategoryService universityCategoryService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("universities", ".xlsx");
            file.transferTo(tempFile);

            ExcelToUniversityMapper mapper = new ExcelToUniversityMapper();
            List<UniversityEntity> universities = mapper.readExcelFile(tempFile.getAbsolutePath());
Map<String, List<CategoryEntity>> categoryEntityMap = mapper.readFacultySheet(tempFile.getAbsolutePath());
           universityCategoryService.saveUniversitiesWithCategories(universities,categoryEntityMap);
            //universityService.saveAllUniversities(universities);

            tempFile.delete();

            return "Universities uploaded and saved successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload and process the file.";
        }
    }

    @GetMapping
    public ResponseEntity<List<UniversityEntity>> getAllUniversities() {
        List<UniversityEntity> universities = universityService.getAllUniversities();
        return new ResponseEntity<>(universities, HttpStatus.OK);
    }
    @GetMapping("/universities")
    public ResponseEntity<List<UniversityDto>> getUniversities(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "location") String location,
            @RequestParam(required = false, name = "website") String website,
            @RequestParam(required = false, name = "rank") Integer rank,
            @RequestParam(required = false, name = "admissionRequirements") String admissionRequirements
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("location", location);
        map.put("website", website);
        map.put("rank", rank);
        map.put("admission_requirements", admissionRequirements);

        map.values().removeAll(Collections.singleton(null));

        List<UniversityEntity> universities = universityService.getUniversitiesAllParams(map);

        List<UniversityDto> universityDtos = universities.stream()
                .map(university -> mapper(university))
                .collect(Collectors.toList());

        return ResponseEntity.ok(universityDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityEntity> getUniversityById(@PathVariable  Integer id) {
        Optional<UniversityEntity> university = universityService.getUniversityById(id);
        return university.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/categories")
    public List<CategoryEntity> getCategoriesByUniversityId(@PathVariable Integer id) {
        return universityService.getCategoriesByUniversityId(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createUniversity(@RequestBody UniversityEntity universityEntity) {
        UniversityEntity savedUniversity = universityService.save(universityEntity);
        return new ResponseEntity<>(mapper(savedUniversity), HttpStatus.CREATED);
    }

    @PostMapping("/addMore")
    public ResponseEntity<List<UniversityEntity>> addUniversities(@RequestBody List<UniversityEntity> universities) {
        List<UniversityEntity> savedUniversities = universities.stream()
                .map(university -> universityService.save(university))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUniversities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Integer id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.noContent().build();
    }
}