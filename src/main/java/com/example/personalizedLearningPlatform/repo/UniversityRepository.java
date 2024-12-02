package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<UniversityEntity, Integer> {
}
