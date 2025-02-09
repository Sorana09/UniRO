package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CategoryLanguageRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveCatLan(Integer categoryId, Integer languageId) {
        jdbcTemplate.update("INSERT INTO category_language (category_id, language_id) VALUES (?, ?)", categoryId, languageId);
    }


    public Integer findOrInserCategory(CategoryEntity category) {
        String selectQuery = "SELECT id FROM category_entity WHERE name = ?";
        List<Integer> ids = jdbcTemplate.query(selectQuery,
                new Object[]{category.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0);
        }

        String insertQuery = "INSERT INTO category_entity (name) VALUES (?)";
        jdbcTemplate.update(insertQuery, category.getName());

        ids = jdbcTemplate.query(selectQuery,
                new Object[]{category.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0);
        } else {
            throw new RuntimeException("Failed to insert or retrieve category ID");
        }
    }


    public Integer findOrInsertLanguage(LanguageEntity languageEntity) {
        String selectQuery = "SELECT id FROM language_entity WHERE name = ?";
        List<Integer> ids = jdbcTemplate.query(selectQuery,
                new Object[]{languageEntity.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0);
        }

        String insertQuery = "INSERT INTO language_entity (name) VALUES (?)";
        jdbcTemplate.update(insertQuery, languageEntity.getName());

        ids = jdbcTemplate.query(selectQuery,
                new Object[]{languageEntity.getName()},
                (rs, rowNum) -> rs.getInt("id"));

        if (!ids.isEmpty()) {
            return ids.get(0);
        } else {
            throw new RuntimeException("Failed to insert or retrieve language ID");
        }
    }

    public void linkCategoryWithLanguage(Integer categoryId, Integer languageId) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM category_language WHERE category_id = ? AND language_id = ?", new Object[]{categoryId, languageId}, Integer.class);

        if (count == 0) {
            jdbcTemplate.update("INSERT INTO category_language (category_id, language_id) VALUES (?, ?)", categoryId, languageId);
        }
    }
}
