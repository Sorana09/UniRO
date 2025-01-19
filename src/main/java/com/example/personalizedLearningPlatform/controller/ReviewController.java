package com.example.personalizedLearningPlatform.controller;


import com.example.personalizedLearningPlatform.dto.ReviewDto;
import com.example.personalizedLearningPlatform.entity.ReviewEntity;
import com.example.personalizedLearningPlatform.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.personalizedLearningPlatform.dto.mapper.Mapper.mapper;

@RestController
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDto>> getReviews() {
        List<ReviewEntity> entities = reviewService.getAllReviews();
        if(!entities.isEmpty())
             return ResponseEntity.ok(entities.stream().map(entity ->mapper(entity)).collect(Collectors.toList()));
        else
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/reviews/user/{id}")
    public ResponseEntity<List<ReviewDto>> getReviewByUserId(@PathVariable(name = "id") Integer id) {
        List<ReviewEntity> entities = reviewService.getReviewByUserId(id);
        if(!entities.isEmpty())
            return ResponseEntity.ok(entities.stream().map(entity ->mapper(entity)).collect(Collectors.toList()));
        else return ResponseEntity.noContent().build();
    }

    @GetMapping("/reviews/university/{id}")
    public ResponseEntity<List<ReviewDto>> getReviewByUniversityId(@PathVariable(name = "id") Integer id) {
        List<ReviewEntity> entities = reviewService.getReviewByUniversityId(id);
        if(!entities.isEmpty())
            return ResponseEntity.ok(entities.stream().map(entity ->mapper(entity)).collect(Collectors.toList()));
        else return ResponseEntity.noContent().build();
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewEntity> createReview(@RequestBody ReviewEntity reviewEntity) {
        return ResponseEntity.ok(reviewService.save(reviewEntity));
    }

}
