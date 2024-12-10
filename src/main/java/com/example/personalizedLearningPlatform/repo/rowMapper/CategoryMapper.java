package com.example.personalizedLearningPlatform.repo.rowMapper;

import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CategoryMapper implements RowMapper<CategoryEntity> {

    @Override
    public CategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setId(rs.getInt("id"));
        categoryEntity.setName(rs.getString("name"));
        categoryEntity.setUniversityId(rs.getInt("university_id"));
        return categoryEntity;
    }

}
