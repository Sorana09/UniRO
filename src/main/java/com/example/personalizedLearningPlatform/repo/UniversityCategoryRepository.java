package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class UniversityCategoryRepository {
    private final JdbcTemplate jdbcTemplate;
    public void saveUniCat(Integer universityId, Integer categoryId) {
        String insertUniversityCategoryQuery = "INSERT INTO university_category (university_id, category_id) VALUES (?, ?)";
        jdbcTemplate.update(insertUniversityCategoryQuery, universityId, categoryId);
    }


    public Integer findOrInsertUniversity(UniversityEntity university) {
        // Verificăm dacă universitatea există deja
        String selectQuery = "SELECT id FROM university_entity WHERE name = ?";
        List<Integer> ids = jdbcTemplate.query(selectQuery,
                new Object[]{university.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0); // Dacă există, returnăm ID-ul
        }

        // Inserăm universitatea în baza de date
        String insertQuery = "INSERT INTO university_entity (name, location, website, rank, admission_requirements) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertQuery,
                university.getName(),
                university.getLocation(),
                university.getWebsite(),
                university.getRank(),
                university.getAdmissionRequirements());

        // Re-executăm interogarea pentru a prelua ID-ul inserat
        ids = jdbcTemplate.query(selectQuery,
                new Object[]{university.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0);
        } else {
            throw new RuntimeException("Failed to insert or retrieve university ID");
        }
    }


    public Integer findOrInsertCategory(CategoryEntity category) {
        // Verificăm dacă categoria există deja
        String selectQuery = "SELECT id FROM category_entity WHERE name = ?";
        List<Integer> ids = jdbcTemplate.query(selectQuery,
                new Object[]{category.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0); // Dacă există, returnăm ID-ul
        }

        // Inserăm categoria în baza de date
        String insertQuery = "INSERT INTO category_entity (name) VALUES (?)";
        jdbcTemplate.update(insertQuery, category.getName());

        // Re-executăm interogarea pentru a prelua ID-ul inserat
        ids = jdbcTemplate.query(selectQuery,
                new Object[]{category.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0);
        } else {
            throw new RuntimeException("Failed to insert or retrieve category ID");
        }
    }



    public void linkUniversityWithCategory(Integer universityId, Integer categoryId) {
        String selectQuery = "SELECT COUNT(*) FROM university_category WHERE university_id = ? AND category_id = ?";
        Integer count = jdbcTemplate.queryForObject(selectQuery, new Object[]{universityId, categoryId}, Integer.class);

        if (count == 0) {
            String insertQuery = "INSERT INTO university_category (university_id, category_id) VALUES (?, ?)";
            jdbcTemplate.update(insertQuery, universityId, categoryId);
        }
    }
}


