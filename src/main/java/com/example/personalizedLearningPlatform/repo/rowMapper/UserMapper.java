package com.example.personalizedLearningPlatform.repo.rowMapper;

import com.example.personalizedLearningPlatform.entity.UserEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<UserEntity> {
    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(rs.getInt("id"));
        userEntity.setEmail(rs.getString("email"));
        userEntity.setFirst_name(rs.getString("first_name"));
        userEntity.setLast_name(rs.getString("last_name"));
        userEntity.setHashed_password(rs.getString("hashed_password"));

        return userEntity;
    }
}
