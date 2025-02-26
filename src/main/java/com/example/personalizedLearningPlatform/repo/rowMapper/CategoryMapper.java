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
        categoryEntity.setDescription(rs.getString("description"));
        categoryEntity.setLatitude(rs.getDouble("latitude"));
        categoryEntity.setLongitude(rs.getDouble("longitude"));
        categoryEntity.setEntranceMethod(rs.getString("entrance_method"));
        return categoryEntity;
    }

}
