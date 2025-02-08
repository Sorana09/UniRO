package com.example.personalizedLearningPlatform.repo.categoryRepositories;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.CategoryMapper;
import com.example.personalizedLearningPlatform.repo.rowMapper.LanguageMapper;
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
public class LanguageRepository {
    private final JdbcTemplate jdbcTemplate;
    private final LanguageMapper languageMapper;
    private final CategoryMapper categoryMapper;

    public LanguageEntity save(LanguageEntity languageEntity) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO language_entity (name) VALUES (?)";

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                        ps.setString(1, languageEntity.getName());
                        return ps;
                    }
                }, keyHolder);


        languageEntity.setId(keyHolder.getKey().intValue());

        return languageEntity;
    }

    public Optional<LanguageEntity> findById(Integer id) {
        List<LanguageEntity> languageEntities = jdbcTemplate.query(
                "SELECT * FROM language_entity WHERE id = ?",
                languageMapper,
                id
        );
        return languageEntities.isEmpty() ? Optional.empty() : Optional.of(languageEntities.get(0));
    }

    public LanguageEntity findByName(String name) {

        String query = "SELECT * FROM language_entity WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{name}, new LanguageMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<LanguageEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM language_entity", languageMapper);
    }

    public List<LanguageEntity> findLanguageById(Integer languageId) {
        return jdbcTemplate.query(
                "SELECT * FROM language_entity WHERE id = ?",
                languageMapper,
                languageId
        );
    }

    public List<CategoryEntity> getCategoryByLanguage(Integer languageId) {
        String query = "SELECT u.* FROM category_entity u " +
                "JOIN category_language uc ON u.id = uc.category_id " +
                "WHERE uc.language_id = ?";
        return jdbcTemplate.query(query,new Object[]{languageId}, categoryMapper);
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM language_entity WHERE id = ?", id);
    }
}
