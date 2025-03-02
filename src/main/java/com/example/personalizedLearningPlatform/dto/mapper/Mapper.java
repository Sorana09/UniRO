package com.example.personalizedLearningPlatform.dto.mapper;

import com.example.personalizedLearningPlatform.dto.*;
import com.example.personalizedLearningPlatform.entity.*;

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
                .is_admin(userEntity.getIsAdmin())
                .interestsAndHobbies(userEntity.getInterestsAndHobbies())
                .suitableCities(userEntity.getSuitableCities())
                .recommandation(userEntity.getRecommandation())
                .build();

    }

    public static UniversityDto mapper(UniversityEntity universityEntity) {
        if (universityEntity == null) {
            return null;
        }
        List<CategoryDto> categoryDtos = null;
        if (universityEntity.getCategoryEntities() != null) {
            categoryDtos = universityEntity.getCategoryEntities().stream()
                    .map(it -> mapper(it))
                    .collect(Collectors.toList());
        }
        return UniversityDto.builder()
                .id(universityEntity.getId())
                .name(universityEntity.getName())
                .location(universityEntity.getLocation())
                .website(universityEntity.getWebsite())
                .rank(universityEntity.getRank())
                .categories(categoryDtos)
                .admissionRequirements(universityEntity.getAdmissionRequirements())
                .latitude(universityEntity.getLatitude())
                .longitude(universityEntity.getLongitude())
                .description(universityEntity.getDescription())
                .build();
    }

    public static CategoryDto mapper(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }
        return CategoryDto.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .latitude(categoryEntity.getLatitude())
                .longitude(categoryEntity.getLongitude())
                .entranceMethod(categoryEntity.getEntranceMethod())
                .build();
    }

    public static SessionDto mapper(SessionEntity sessionEntity) {
        if (sessionEntity == null) {
            return null;
        }
        return SessionDto.builder()
                .expiredAt(sessionEntity.getExpiredAt())
                .sessionKey(sessionEntity.getSessionKey())
                .userId(sessionEntity.getUserId())
                .id(sessionEntity.getId())
                .build();
    }

    public static ReviewDto mapper(ReviewEntity reviewEntity) {
        if (reviewEntity == null) {
            return null;
        }
        return ReviewDto.builder()
                .id(reviewEntity.getId())
                .message(reviewEntity.getMessage())
                .universityId(reviewEntity.getUniversityId())
                .userId(reviewEntity.getUserId())
                .wroteAt(reviewEntity.getWroteAt())
                .build();
    }

    public static LanguageDto mapper(LanguageEntity languageEntity) {
        if (languageEntity == null) {
            return null;
        }
        return LanguageDto.builder()
                .id(languageEntity.getId())
                .name(languageEntity.getName())
                .build();
    }
    public static StudyProgramDto mapper(StudyProgramEntity studyProgramEntity) {
        if (studyProgramEntity == null) {
            return null;
        }
        return StudyProgramDto.builder()
                .id(studyProgramEntity.getId())
                .name(studyProgramEntity.getName())
                .build();
    }


    public static UserActivityDto mapper(UserActivityEntity userActivityEntity) {
        if (userActivityEntity == null) {
            return null;
        }
        return UserActivityDto.builder()
                .id(userActivityEntity.getId())
                .userId(userActivityEntity.getUserId())
                .action(userActivityEntity.getAction())
                .endpoint(userActivityEntity.getEndpoint())
                .timestamp(userActivityEntity.getTimestamp())
                .build();
    }
}
