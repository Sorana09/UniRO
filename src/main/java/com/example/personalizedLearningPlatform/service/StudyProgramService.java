package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.StudyProgramEntity;
import com.example.personalizedLearningPlatform.repo.StudyProgramRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudyProgramService {
    private final StudyProgramRepository studyProgramRepository;
    public List<StudyProgramEntity> getAllStudyPrograms() {
        return studyProgramRepository.findAll();
    }
    public Optional<StudyProgramEntity> getStudyProgramById(Integer id) {
        return studyProgramRepository.findById(id);
    }
    public StudyProgramEntity createStudyProgram(StudyProgramEntity studyProgramEntity) {
        return studyProgramRepository.save(studyProgramEntity);
    }
    public void deleteStudyProgramById(Integer id) {
        studyProgramRepository.deleteById(id);
    }

}
