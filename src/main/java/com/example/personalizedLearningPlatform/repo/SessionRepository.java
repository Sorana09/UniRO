package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.SessionEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.SessionMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SessionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SessionMapper sessionMapper;

    public List<SessionEntity> find(Integer userId) {
        List<SessionEntity> sessionEntities = jdbcTemplate.query("select * from sessions where user_id=?", new Object[]{userId}, new SessionMapper());
        return sessionEntities;
    }

    public List<SessionEntity> getSessions() {
        return jdbcTemplate.query("select * from sessions",sessionMapper);
    }

    public void insert(SessionEntity sessionEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO sessions (session_key, expires_at, user_id) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, sessionEntity.getSessionKey());
            ps.setObject(2, sessionEntity.getExpiredAt());
            //ps.setTimestamp(2, Timestamp.valueOf(sessionEntity.getExpiredAt().toLocalDateTime()));
            ps.setInt(3, sessionEntity.getUserId());
            return ps;
        }, keyHolder);

        sessionEntity.setId(keyHolder.getKey().intValue());
    }

    public Optional<SessionEntity> getByKey(String key) {
        List<SessionEntity> sessionEntity = jdbcTemplate.query("SELECT * FROM sessions where session_key=? ", sessionMapper, key);
        return sessionEntity.isEmpty() ? Optional.empty() : Optional.of(sessionEntity.get(0));
    }

    public void deleteByKey(String key) {
        jdbcTemplate.update("DELETE FROM sessions where session_key=?", key);
    }
}
