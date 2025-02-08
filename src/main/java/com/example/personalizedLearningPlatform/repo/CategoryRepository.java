package com.example.personalizedLearningPlatform.repo;


import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.CategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CategoryMapper categoryMapper;

    public CategoryEntity save(CategoryEntity category) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO category_entity (name) VALUES (?)";

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                        ps.setString(1, category.getName());
                        return ps;
                    }
                }, keyHolder);


        category.setId(keyHolder.getKey().intValue());

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