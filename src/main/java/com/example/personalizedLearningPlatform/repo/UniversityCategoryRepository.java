package com.example.personalizedLearningPlatform.repo;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UniversityCategoryRepository {
    private final JdbcTemplate jdbcTemplate;
    public void saveUniCat(Integer universityId, Integer categoryId) {
        String insertUniversityCategoryQuery = "INSERT INTO university_category (university_id, category_id) VALUES (?, ?)";
        jdbcTemplate.update(insertUniversityCategoryQuery, universityId, categoryId);
    }
}
