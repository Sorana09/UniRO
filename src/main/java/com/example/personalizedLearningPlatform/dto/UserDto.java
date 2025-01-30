package com.example.personalizedLearningPlatform.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String first_name;
    private String last_name;
    private String email;
    private String hashed_password;
    private Boolean is_admin;

}