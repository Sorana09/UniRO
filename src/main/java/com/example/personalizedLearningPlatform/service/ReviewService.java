package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.ReviewEntity;
import com.example.personalizedLearningPlatform.repo.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<ReviewEntity> getAllReviews() {
       return reviewRepository.findAllReviews();
    }
    public List<ReviewEntity> getReviewByUserId(Integer id) {
        return reviewRepository.findReviewByUserId(id);
    }
    public List<ReviewEntity> getReviewByUniversityId(Integer id) {
        return reviewRepository.findReviewByUniversityId(id);
    }
    public ReviewEntity save(ReviewEntity reviewEntity) {
        //reviewEntity.setWroteAt(null);
        reviewRepository.save(reviewEntity);
        return reviewEntity;
    }
}
