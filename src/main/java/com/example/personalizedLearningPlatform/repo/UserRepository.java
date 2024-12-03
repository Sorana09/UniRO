package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.UserEntity;

import com.example.personalizedLearningPlatform.repo.rowMapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        jdbcTemplate.update(
                "INSERT INTO user_entity (id, email, first_name, last_name, hashed_password) VALUES (?, ?, ?, ?, ?)",
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getHashedPassword()
        );
        return user;
    }

    public Optional<UserEntity> findById(Long id) {
        List<UserEntity> users = jdbcTemplate.query(
                "SELECT * FROM user_entity WHERE id = ?",
                userMapper,
                id
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public Optional<UserEntity> findByEmail(String email) {
        List<UserEntity> users = jdbcTemplate.query(
                "SELECT * FROM user_entity WHERE email = ?",
                userMapper,
                email
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