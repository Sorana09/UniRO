package com.example.personalizedLearningPlatform.repo;


import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.UniversityMapper;
import com.example.personalizedLearningPlatform.sqlMethods.SQLMethod;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.personalizedLearningPlatform.sqlMethods.SQLMethod.getSql;

@Repository
@AllArgsConstructor
public class UniversityRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UniversityMapper universityMapper;
    private final CategoryRepository categoryRepository;


    public List<UniversityEntity> findAll() {
        String universityQuery = "SELECT * FROM university_entity";
        List<UniversityEntity> universities = jdbcTemplate.query(universityQuery, universityMapper);
        universities.forEach(university -> {
            List<CategoryEntity> categories = categoryRepository.findByUniversityId(university.getId());
            university.setCategoryEntities(categories);
        });
        return universities;
    }

    public List<UniversityEntity> findAllParams(Map<String, Object> params) {
        String sql = "SELECT * FROM notifications " + getSql(params);
        return jdbcTemplate.query(sql, params.values().toArray(), universityMapper);
    }
    public UniversityEntity findById(Integer id) {
        String universityQuery = "SELECT * FROM university_entity WHERE id = ?";
        UniversityEntity university = jdbcTemplate.queryForObject(universityQuery, universityMapper, id);
        if (university != null) {
            List<CategoryEntity> categories = categoryRepository.findByUniversityId(university.getId());
            university.setCategoryEntities(categories);
        }
        return university;
    }

    public UniversityEntity save(UniversityEntity university) {
        String insertQuery = "INSERT INTO university_entity (name, location, website, rank, admission_requirements) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, university.getName());
            ps.setString(2, university.getLocation());
            ps.setString(3, university.getWebsite());
            ps.setInt(4, university.getRank());
            ps.setString(5, university.getAdmissionRequirements());
            return ps;
        }, keyHolder);

        Integer generatedUniversityId = keyHolder.getKey().intValue();
        university.setId(generatedUniversityId);

        if (university.getCategoryEntities() != null) {
            university.getCategoryEntities().forEach(category -> {
                category.setUniversityId(generatedUniversityId);
                categoryRepository.save(category);
            });
        }

        return university;
    }

    public int update(UniversityEntity university) {
        String updateQuery = "UPDATE university_entity SET name = ?, location = ?, website = ?, rank = ?, admission_requirements = ? WHERE id = ?";
        return jdbcTemplate.update(updateQuery,
                university.getName(),
                university.getLocation(),
                university.getWebsite(),
                university.getRank(),
                university.getAdmissionRequirements(),
                university.getId());
    }

    public int delete(Integer id) {
        return jdbcTemplate.update("DELETE FROM university_entity WHERE id = ?", id);
    }
}
