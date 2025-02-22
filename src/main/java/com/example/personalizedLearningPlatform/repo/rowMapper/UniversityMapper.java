package com.example.personalizedLearningPlatform.repo.rowMapper;

import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Component
public class UniversityMapper implements RowMapper<UniversityEntity> {

    @Override
    public UniversityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UniversityEntity.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .location(rs.getString("location"))
                .website(rs.getString("website"))
                .rank(rs.getInt("rank"))
                .admissionRequirements(rs.getString("admission_requirements"))
                .latitude(rs.getDouble("latitude"))
                .longitude(rs.getDouble("longitude"))
                .build();
    }

    private List<String> parseDomains(String domains) {
        if (domains == null || domains.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(domains.split(","));
    }
}
