package com.example.personalizedLearningPlatform.repo;


import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import com.example.personalizedLearningPlatform.entity.StudyProgramEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.CategoryMapper;
import com.example.personalizedLearningPlatform.repo.rowMapper.LanguageMapper;
import com.example.personalizedLearningPlatform.repo.rowMapper.StudyProgramMapper;
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
    private final LanguageMapper languageMapper;
    private final StudyProgramMapper studyProgramMapper;

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

    public List<LanguageEntity> getLanguageByCategoryId(Integer categoryId) {
        String query = "SELECT u.* FROM language_entity u " +
                "JOIN category_language uc ON u.id = uc.language_id " +
                "WHERE uc.category_id = ?";
        return jdbcTemplate.query(query,new Object[]{categoryId},languageMapper);
    }

    public List<StudyProgramEntity> getStudyProgramByCategoryId(Integer categoryId) {
        String query = "SELECT u.* FROM study_program_entity u " +
                "JOIN category_study_program uc ON u.id = uc.study_program_id " +
                "WHERE uc.category_id = ?";
        return jdbcTemplate.query(query,new Object[]{categoryId},studyProgramMapper);
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

    public void updateDescription(Integer id, String description){
        jdbcTemplate.update("UPDATE category_entity SET description = ? WHERE id = ?", description, id);
    }

    public void updateEntranceMethod(Integer id, String entranceMethod){
        jdbcTemplate.update("UPDATE category_entity SET entrance_method = ? WHERE id = ?", entranceMethod, id);
    }

    public void updateCoordinates(Integer id, Double latitude, Double longitude){
        jdbcTemplate.update("UPDATE category_entity SET latitude = ?, longitude = ? WHERE id = ?", latitude, longitude, id);
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM category_entity WHERE id = ?", id);
    }
}