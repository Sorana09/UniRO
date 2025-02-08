package com.example.personalizedLearningPlatform.repo.userRepositories;

import com.example.personalizedLearningPlatform.entity.ReviewEntity;
import com.example.personalizedLearningPlatform.repo.rowMapper.ReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
@AllArgsConstructor
public class ReviewRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ReviewMapper reviewMapper;

    public List<ReviewEntity> findAllReviews() {
        return jdbcTemplate.query("SELECT * FROM review_entity", reviewMapper);
    }

    public List<ReviewEntity> findReviewByUserId(Integer userId) {
        return jdbcTemplate.query("SELECT * FROM review_entity WHERE user_id = ?", reviewMapper, userId);
    }

    public List<ReviewEntity> findReviewByUniversityId(Integer universityId) {
        return jdbcTemplate.query("SELECT * FROM review_entity WHERE review_entity.university_id = ?", reviewMapper, universityId);
    }

    public void save(ReviewEntity reviewEntity) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO review_entity (message, wrote_at, user_id, university_id) VALUES (?,?,?,?)",
                    new String[]{"id"}
            );
            ps.setString(1, reviewEntity.getMessage());
            //ps.setObject(2, reviewEntity.getWroteAt());
            ps.setTimestamp(2, Timestamp.valueOf(reviewEntity.getWroteAt().toLocalDateTime()));
            ps.setInt(3, reviewEntity.getUserId());
            ps.setInt(4, reviewEntity.getUniversityId());
            return ps;
        }, keyHolder);

        reviewEntity.setId(keyHolder.getKey().intValue());
    }

}
