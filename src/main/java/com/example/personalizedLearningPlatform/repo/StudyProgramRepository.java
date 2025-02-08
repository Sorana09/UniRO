package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.StudyProgramEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.CategoryMapper;
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
public class StudyProgramRepository {
    private final JdbcTemplate jdbcTemplate;
    private final StudyProgramMapper studyProgramMapper;
    private final CategoryMapper categoryMapper;

    public StudyProgramEntity save(StudyProgramEntity studyProgramEntity) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO study_program_entity (name) VALUES (?)";

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                        ps.setString(1, studyProgramEntity.getName());
                        return ps;
                    }
                }, keyHolder);


        studyProgramEntity.setId(keyHolder.getKey().intValue());

        return studyProgramEntity;
    }

    public Optional<StudyProgramEntity> findById(Integer id) {
        List<StudyProgramEntity> studyProgramEntities = jdbcTemplate.query(
                "SELECT * FROM study_program_entity WHERE id = ?",
                studyProgramMapper,
                id
        );
        return studyProgramEntities.isEmpty() ? Optional.empty() : Optional.of(studyProgramEntities.get(0));
    }

    public StudyProgramEntity findByName(String name) {

        String query = "SELECT * FROM study_program_entity WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(query,studyProgramMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<StudyProgramEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM study_program_entity", studyProgramMapper);
    }

    public List<StudyProgramEntity> findByUStudyProgramId(Integer studyId) {
        return jdbcTemplate.query(
                "SELECT * FROM study_program_entity WHERE id = ?",
                studyProgramMapper,
                studyId
        );
    }

    public List<CategoryEntity> getCategoriesByStudyProram(Integer studyId) {
        String query = "SELECT u.* FROM category_entity u " +
                "JOIN category_study_program uc ON u.id = uc.category_id " +
                "WHERE uc.study_program_id = ?";
        return jdbcTemplate.query(query, new Object[]{studyId},categoryMapper);
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM study_program_entity WHERE id = ?", id);
    }
}
