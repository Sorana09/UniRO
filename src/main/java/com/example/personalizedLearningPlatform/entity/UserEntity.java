package com.example.personalizedLearningPlatform.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String hashedPassword;

}

