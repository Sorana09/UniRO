package com.example.personalizedLearningPlatform.repo.rowMapper;

import com.example.personalizedLearningPlatform.entity.StudyProgramEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class StudyProgramMapper implements RowMapper<StudyProgramEntity> {
    @Override
    public StudyProgramEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        StudyProgramEntity studyProgramEntity = new StudyProgramEntity();
        studyProgramEntity.setId(rs.getInt("id"));
        studyProgramEntity.setName(rs.getString("name"));
        return studyProgramEntity;
    }
}
