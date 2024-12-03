package com.example.personalizedLearningPlatform.repo;


import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.UniversityMapper;
import com.example.personalizedLearningPlatform.sqlMethods.SQLMethod;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.personalizedLearningPlatform.sqlMethods.SQLMethod.getSql;

@Repository
public class UniversityRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UniversityMapper universityMapper;

    public UniversityRepository(JdbcTemplate jdbcTemplate, UniversityMapper universityMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.universityMapper = universityMapper;
    }

    public UniversityEntity save(UniversityEntity university) {
        jdbcTemplate.update(
                "INSERT INTO university_entity (id, name, location, website, rank, admission_requirements) VALUES (?, ?, ?, ?, ?, ?)",
                university.getId(),
                university.getName(),
                university.getLocation(),
                university.getWebsite(),
                university.getRank(),
                university.getAdmissionRequirements()
        );
        return university;
    }

    public Optional<UniversityEntity> findById(Integer id) {
        List<UniversityEntity> universities = jdbcTemplate.query(
                "SELECT * FROM university_entity WHERE id = ?",
                universityMapper,
                id
        );
        return universities.isEmpty() ? Optional.empty() : Optional.of(universities.get(0));
    }

    public List<UniversityEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM university_entity", universityMapper);
    }
    public List<UniversityEntity> findAllParams(Map<String, Object> params) {
        String sql = "SELECT * FROM notifications " + getSql(params);
        return jdbcTemplate.query(sql, params.values().toArray(), universityMapper);
    }

    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM university_entity WHERE id = ?", id);
    }
}