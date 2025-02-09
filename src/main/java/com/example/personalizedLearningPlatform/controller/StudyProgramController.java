package com.example.personalizedLearningPlatform.controller;

import com.example.personalizedLearningPlatform.dto.LanguageDto;
import com.example.personalizedLearningPlatform.dto.StudyProgramDto;
import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import com.example.personalizedLearningPlatform.entity.StudyProgramEntity;
import com.example.personalizedLearningPlatform.service.StudyProgramService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.personalizedLearningPlatform.dto.mapper.Mapper.mapper;

@RestController
@RequestMapping("/study-programs")
@AllArgsConstructor
public class StudyProgramController {
    private final StudyProgramService studyProgramService;

    @GetMapping
    public List<StudyProgramEntity> getAllStudyPrograms() {
        return studyProgramService.getAllStudyPrograms();
    }

    @GetMapping("{id}")
    public ResponseEntity<StudyProgramDto> getStudyProgramById(@PathVariable Integer id) {
        if(studyProgramService.getStudyProgramById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper(studyProgramService.getStudyProgramById(id).get()));
    }

    @PostMapping
    public ResponseEntity<StudyProgramDto> createLanguage(@RequestBody StudyProgramEntity studyProgramEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper(studyProgramService.createStudyProgram(studyProgramEntity)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudyProgramById(@PathVariable Integer id) {
        if(studyProgramService.getStudyProgramById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        studyProgramService.deleteStudyProgramById(id);
        return ResponseEntity.ok().build();
    }
}
