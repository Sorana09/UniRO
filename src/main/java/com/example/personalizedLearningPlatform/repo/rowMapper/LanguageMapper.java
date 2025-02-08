package com.example.personalizedLearningPlatform.repo.rowMapper;

import com.example.personalizedLearningPlatform.entity.LanguageEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LanguageMapper implements RowMapper<LanguageEntity> {
    @Override
    public LanguageEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setId(rs.getInt("id"));
        languageEntity.setName(rs.getString("name"));
        return languageEntity;
    }
}
