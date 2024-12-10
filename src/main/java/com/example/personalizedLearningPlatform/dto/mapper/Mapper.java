package com.example.personalizedLearningPlatform.dto.mapper;

import com.example.personalizedLearningPlatform.dto.CategoryDto;
import com.example.personalizedLearningPlatform.dto.UniversityDto;
import com.example.personalizedLearningPlatform.dto.UserDto;
import com.example.personalizedLearningPlatform.dto.enums.AdmmisionRequirementsDto;
import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.CategoryMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    public static UserDto mapper(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return UserDto.builder()
                .id(userEntity.getId())
                .first_name(userEntity.getFirstName())
                .last_name(userEntity.getLastName())
                .email(userEntity.getEmail())
                .hashed_password(userEntity.getHashedPassword())
                .build();

    }

    public static UniversityDto mapper(UniversityEntity universityEntity) {
        if (universityEntity == null) {
            return null;
        }
        List<CategoryDto> categoryDtos = null;
        if (universityEntity.getCategoryEntities() != null) {
            categoryDtos = universityEntity.getCategoryEntities().stream()
                    .map(it-> mapper(it))
                    .collect(Collectors.toList());
        }
        return UniversityDto.builder()
                .id(universityEntity.getId())
                .name(universityEntity.getName())
                .location(universityEntity.getLocation())
                .website(universityEntity.getWebsite())
                .rank(universityEntity.getRank())
                .categories(categoryDtos)
                .admissionRequirements(AdmmisionRequirementsDto.valueOf(universityEntity.getAdmissionRequirements()))
                .build();
    }

    public static CategoryDto mapper(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        return CategoryDto.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .universityId(categoryEntity.getUniversityId())
                .build();
    }
}
