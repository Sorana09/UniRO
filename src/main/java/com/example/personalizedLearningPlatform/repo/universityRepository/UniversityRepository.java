package com.example.personalizedLearningPlatform.repo.universityRepository;


import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.categoryRepositories.CategoryRepository;
import com.example.personalizedLearningPlatform.repo.rowMapper.CategoryMapper;
import com.example.personalizedLearningPlatform.repo.rowMapper.UniversityMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

import static com.example.personalizedLearningPlatform.sqlMethods.SQLMethod.getSql;

@Repository
@AllArgsConstructor
public class UniversityRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UniversityMapper universityMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final UniversityCategoryRepository universityCategoryRepository;


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

    public UniversityEntity save(UniversityEntity universityEntity) {

        String insertQuery = "INSERT INTO university_entity (id, name, location, website, rank, admission_requirements) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, universityEntity.getId());
            ps.setString(2, universityEntity.getName());
            ps.setString(3, universityEntity.getLocation());
            ps.setString(4, universityEntity.getWebsite());
            ps.setInt(5, universityEntity.getRank());
            ps.setString(6, universityEntity.getAdmissionRequirements());
            return ps;
        };

        jdbcTemplate.update(psc, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            universityEntity.setId(((Number) keys.get("id")).intValue());
        }

        if (universityEntity.getCategoryEntities() != null) {
            Set<CategoryEntity> uniqueCategories = new HashSet<>();

            for (CategoryEntity categoryEntity : universityEntity.getCategoryEntities()) {

                CategoryEntity existingCategory = categoryRepository.findByName(categoryEntity.getName());

                if (existingCategory == null) {
                    CategoryEntity newCategory = CategoryEntity.builder()
                            .name(categoryEntity.getName())
                            .build();
                    categoryRepository.save(newCategory);
                    uniqueCategories.add(newCategory);
                } else {

                    uniqueCategories.add(existingCategory);
                }
            }

            universityEntity.setCategoryEntities(new ArrayList<>(uniqueCategories));

            for (CategoryEntity categoryEntity : uniqueCategories) {
                universityCategoryRepository.saveUniCat(universityEntity.getId(), categoryEntity.getId());
            }
        }

        return universityEntity;
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

    public List<CategoryEntity> getCategoriesByUniversityId(Integer universityId) {
        String sql = "SELECT c.* FROM category_entity c JOIN university_category uc ON c.id = uc.category_id WHERE uc.university_id = ?";
        return jdbcTemplate.query(sql, categoryMapper, universityId);
    }
}