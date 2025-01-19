package com.example.personalizedLearningPlatform.repo;

import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.UniversityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UniversityRepoExtract {


    private final JdbcTemplate jdbcTemplate;
    private final UniversityMapper universityMapper;


    public void saveAllUniversities(List<UniversityEntity> universities) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO university_entity ( name, location, website, rank, admission_requirements) " +
                        "VALUES ( ?, ?, ?, ?, ?)",
                universities,
                universities.size(),
                (ps, university) -> {
                    //ps.setInt(1, university.getId());
                    ps.setString(1, university.getName());
                    ps.setString(2, university.getLocation());
                    ps.setString(3, university.getWebsite());
                    ps.setObject(4, university.getRank());
                    ps.setString(5, university.getAdmissionRequirements());
                }
        );

    }

    public Optional<UniversityEntity> findByName(String name) {
        List<UniversityEntity> univ = jdbcTemplate.query(
                "SELECT * FROM university_entity WHERE name = ?",
                universityMapper,
                name
        );
        return univ.isEmpty() ? Optional.empty() : Optional.of(univ.get(0));
    }
}

