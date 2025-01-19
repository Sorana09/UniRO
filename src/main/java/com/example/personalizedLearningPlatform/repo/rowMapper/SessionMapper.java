package com.example.personalizedLearningPlatform.repo.rowMapper;

import com.example.personalizedLearningPlatform.entity.SessionEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@Component
public class SessionMapper implements RowMapper<SessionEntity> {
    @Override
    public SessionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        SessionEntity sessionEntity = new SessionEntity();

        sessionEntity.setId(rs.getInt("id"));
        sessionEntity.setSessionKey(rs.getString("session_key"));
        sessionEntity.setExpiredAt(rs.getObject("expires_at", OffsetDateTime.class));
        sessionEntity.setUserId(rs.getInt("user_id"));


        return sessionEntity;
    }
}
