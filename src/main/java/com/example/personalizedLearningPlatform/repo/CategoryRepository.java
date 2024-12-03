package com.example.personalizedLearningPlatform.repo;


import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.CategoryMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CategoryMapper categoryMapper;

    public CategoryRepository(JdbcTemplate jdbcTemplate, CategoryMapper categoryMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryMapper = categoryMapper;
    }

    public CategoryEntity save(CategoryEntity category) {
        jdbcTemplate.update(
                "INSERT INTO category_entity (id, name, user_id) VALUES (?, ?, ?)",
                category.getId(),
                category.getName(),
                category.getUserId()
        );
        return category;
    }

    public Optional<CategoryEntity> findById(Integer id) {
        List<CategoryEntity> categories = jdbcTemplate.query(
                "SELECT * FROM category_entity WHERE id = ?",
                categoryMapper,
                id
        );
        return categories.isEmpty() ? Optional.empty() : Optional.of(categories.get(0));
    }

    public List<CategoryEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM category_entity", categoryMapper);
    }

    public List<CategoryEntity> findByUserId(Integer userId) {
        return jdbcTemplate.query(
                "SELECT * FROM category_entity WHERE user_id = ?",
                categoryMapper,
                userId
        );
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM category_entity WHERE id = ?", id);
    }
}