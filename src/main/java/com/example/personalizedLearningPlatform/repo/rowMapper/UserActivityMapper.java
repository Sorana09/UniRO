package com.example.personalizedLearningPlatform.repo.rowMapper;

import com.example.personalizedLearningPlatform.entity.UserActivityEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@Component
public class UserActivityMapper implements RowMapper<UserActivityEntity> {
    @Override
    public UserActivityEntity mapRow(ResultSet rs, int intNow) throws SQLException {
        return UserActivityEntity.builder()
                .id(rs.getInt("id"))
                .userId(rs.getInt("user_id"))
                .action(rs.getString("action"))
                .endpoint(rs.getString("endpoint"))
                .timestamp(rs.getObject("timestamp", OffsetDateTime.class))
                .build();
    }
}
