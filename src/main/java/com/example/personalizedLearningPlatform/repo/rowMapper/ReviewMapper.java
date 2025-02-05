package com.example.personalizedLearningPlatform.repo.rowMapper;

import com.example.personalizedLearningPlatform.entity.ReviewEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@Component
public class ReviewMapper implements RowMapper<ReviewEntity> {
    @Override
    public ReviewEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReviewEntity reviewEntity = new ReviewEntity();

        reviewEntity.setId(rs.getInt("id"));
        reviewEntity.setMessage(rs.getString("message"));
        reviewEntity.setWroteAt(rs.getObject("wrote_at", OffsetDateTime.class));
        reviewEntity.setUserId(rs.getInt("user_id"));
        reviewEntity.setUniversityId(rs.getInt("university_id"));

        return reviewEntity;
    }
}