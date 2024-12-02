package com.example.personalizedLearningPlatform.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String hashed_password;

    public UserDto(Integer id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.first_name = firstName;
        this.last_name = lastName;
    }
}