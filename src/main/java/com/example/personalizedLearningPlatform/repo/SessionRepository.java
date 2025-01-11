package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.SessionEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.SessionMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SessionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SessionMapper sessionMapper;

    public List<SessionEntity> find(Long userId){
        List<SessionEntity> sessionEntities = jdbcTemplate.query("select * from sessions where user_id=?", new Object[]{userId}, new SessionMapper());
        return sessionEntities;
    }

    public void insert(SessionEntity sessionEntity){
        jdbcTemplate.update("INSERT INTO sessions (user_id,expires_at,session_key) VALUES (?,?,?)",
                sessionEntity.getUserId(),sessionEntity.getExpiredAt(),sessionEntity.getSessionKey());
    }

    public Optional<SessionEntity> getByKey(String key){
       List<SessionEntity> sessionEntity = jdbcTemplate.query("SELECT * FROM sessions where session_key=? ",sessionMapper,key);
       return sessionEntity.isEmpty() ? Optional.empty() : Optional.of(sessionEntity.get(0));
    }

    public void deleteByKey(String key){
        jdbcTemplate.update("DELETE FROM sessions where session_key=?",key);
    }
}
