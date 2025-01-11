package com.example.personalizedLearningPlatform.repo;


import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.CategoryMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.EmptyResultDataAccessException;
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
                "INSERT INTO category_entity (id, name) VALUES (?, ?)",
                category.getId(),
                category.getName()
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
    public CategoryEntity findByName(String name) {

        String query = "SELECT * FROM category_entity WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{name}, new CategoryMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<CategoryEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM category_entity", categoryMapper);
    }

    public List<CategoryEntity> findByUniversityId(Integer universityId) {
        return jdbcTemplate.query(
                "SELECT * FROM category_entity WHERE id = ?",
                categoryMapper,
                universityId
        );
    }
    public List<UniversityEntity> getUniversitiesByCategory(Integer categoryId) {
        String query = "SELECT u.* FROM university_entity u " +
                "JOIN university_category uc ON u.id = uc.university_id " +
                "WHERE uc.category_id = ?";
        return jdbcTemplate.query(query, new Object[]{categoryId}, (rs, rowNum) -> {
            UniversityEntity university = new UniversityEntity();
            university.setId(rs.getInt("id"));
            university.setName(rs.getString("name"));
            university.setLocation(rs.getString("location"));
            university.setWebsite(rs.getString("website"));
            university.setRank(rs.getInt("rank"));
            university.setAdmissionRequirements(rs.getString("admission_requirements"));
            return university;
        });
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM category_entity WHERE id = ?", id);
    }
}