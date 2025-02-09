package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.StudyProgramEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CategoryStudyProgramRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveCatLan(Integer categoryId, Integer studyId) {
        jdbcTemplate.update("INSERT INTO category_study_program (category_id, study_program_id) VALUES (?, ?)", categoryId, studyId);
    }


    public Integer findOrInserCategory(CategoryEntity categoryEntity) {
        String selectQuery = "SELECT id FROM category_entity WHERE name = ?";
        List<Integer> ids = jdbcTemplate.query(selectQuery,
                new Object[]{categoryEntity.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0);
        }

        jdbcTemplate.update("INSERT INTO category_entity (name) VALUE (?)",
                categoryEntity.getName());

                ids = jdbcTemplate.query(selectQuery,
                        new Object[]{categoryEntity.getName()},
                        (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0);
        } else {
            throw new RuntimeException("Failed to insert or retrieve category ID");
        }
    }


    public Integer findOrInsertStudy(StudyProgramEntity studyProgramEntity) {
        String selectQuery = "SELECT id FROM study_program_entity WHERE name = ?";
        List<Integer> ids = jdbcTemplate.query(selectQuery,
                new Object[]{studyProgramEntity.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0);
        }

        String insertQuery = "INSERT INTO study_program_entity (name) VALUES (?)";
        jdbcTemplate.update(insertQuery, studyProgramEntity.getName());

        ids = jdbcTemplate.query(selectQuery,
                new Object[]{studyProgramEntity.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0);
        } else {
            throw new RuntimeException("Failed to insert or retrieve study program ID");
        }
    }

    public void linkCategoryWithStudyProgram(Integer categoryId, Integer studyProgramId) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM category_study_program WHERE category_id = ? AND study_program_id = ?", new Object[]{categoryId, studyProgramId}, Integer.class);

        if (count == 0) {
            jdbcTemplate.update("INSERT INTO category_study_program (category_id, study_program_id) VALUES (?, ?)", categoryId, studyProgramId);
        }
    }
}
