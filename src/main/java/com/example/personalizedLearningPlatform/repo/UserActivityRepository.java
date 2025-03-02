package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.UserActivityEntity;
import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.UserActivityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class UserActivityRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserActivityMapper userActivityMapper;
    private final UserRepository userRepository;

    public void saveUserActivity(Integer userId, String action, String endpoint) {
        String sql = "INSERT INTO user_activities (user_id, action, endpoint) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, action, endpoint);
    }

    public List<UserActivityEntity> findAll() {
        String sql = "SELECT * FROM user_activities ORDER BY timestamp DESC";
        return jdbcTemplate.query(sql, userActivityMapper);
    }

    public void saveUserActivity(String email, String action, String endpoint) {
        UserEntity user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            log.warn("No user found with email: {}", email);
            return;
        }
        String sql = "INSERT INTO user_activities (user_id, action, endpoint) VALUES (?, ?, ?)";
        try {
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, user.getId());
                ps.setString(2, action);
                ps.setString(3, endpoint);
                return ps;
            });
            log.info("User activity saved: email={}, action={}, endpoint={}, rowsAffected={}", email, action, endpoint, rowsAffected);
        } catch (Exception e) {
            log.error("Error saving user activity: email={}, action={}, endpoint={}", email, action, endpoint, e);
        }
    }

}
