package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public UserRepository(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    public UserEntity save(UserEntity user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO user_entity (email, first_name, last_name, hashed_password) VALUES (?, ?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getHashedPassword());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    public Optional<UserEntity> findById(Integer id) {
        List<UserEntity> users = jdbcTemplate.query(
                "SELECT * FROM user_entity WHERE id = ?",
                userMapper,
                id
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public void updateInterestsAndHobbies(Integer id, String interestsAndHobbies) {
        jdbcTemplate.update("UPDATE user_entity SET interests_and_hobbies = ? WHERE id = ?", interestsAndHobbies, id);
    }

    public void updatCities(Integer id, String cities) {
        jdbcTemplate.update("UPDATE user_entity SET suitable_cities = ? WHERE id = ?", cities, id);
    }
    public void updateRecommendation(Integer id, String recommendation) {
        jdbcTemplate.update("UPDATE user_entity SET recommandation = ? WHERE id = ?", recommendation, id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        List<UserEntity> users = jdbcTemplate.query(
                "SELECT * FROM user_entity WHERE email = ?",
                userMapper,
                email
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public Optional<UserEntity> findByFirstName(String name) {
        List<UserEntity> users = jdbcTemplate.query(
                "SELECT * FROM user_entity WHERE first_name = ?",
                userMapper,
                name
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public List<UserEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM user_entity", userMapper);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM user_entity WHERE id = ?", id);
    }
}