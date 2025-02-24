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
        userEntity.setFirstName(rs.getString("first_name"));
        userEntity.setLastName(rs.getString("last_name"));
        userEntity.setHashedPassword(rs.getString("hashed_password"));
        userEntity.setIsAdmin(rs.getBoolean("is_admin"));
        userEntity.setInterestsAndHobbies(rs.getString("interests_and_hobbies"));
        userEntity.setSuitableCities(rs.getString("suitable_cities"));
        userEntity.setRecommandation(rs.getString("recommandation"));

        return userEntity;
    }
}
