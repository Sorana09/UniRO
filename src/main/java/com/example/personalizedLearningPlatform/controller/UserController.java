package com.example.personalizedLearningPlatform.controller;

import com.example.personalizedLearningPlatform.dto.UserDto;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.exception.EntityNotFoundException;
import com.example.personalizedLearningPlatform.repo.UserRepository;
import com.example.personalizedLearningPlatform.service.OpenAIService;
import com.example.personalizedLearningPlatform.service.UniversityService;
import com.example.personalizedLearningPlatform.service.UserService;
import io.github.d4rckh.limiterx.spring.annotation.RateLimited;
import io.github.d4rckh.limiterx.spring.extractor.IPExtractor;
import io.github.d4rckh.limiterx.spring.extractor.UsernameExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.personalizedLearningPlatform.dto.mapper.Mapper.mapper;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final OpenAIService openAIService;


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Integer id) {
        Optional<UserEntity> user = userService.findById(id);
        return user
                .map(userEntity -> ResponseEntity.ok(mapper(userEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/emails/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable(name = "email") String email) {
        return userService.findByEmail(email)
                .map(userEntity -> ResponseEntity.ok(mapper(userEntity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> users = userService.findAll();
        System.out.println(users.get(0));
        List<UserDto> userDtos = users.stream()
                .map(it -> mapper(it))
                .collect(Collectors.toList());
        if (userDtos.size() > 0)
            return ResponseEntity.ok(userDtos);
        else return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/recommend-faculties")
    public ResponseEntity<String> recommendFaculties(@PathVariable Integer userId) throws IOException {
        UserEntity user = userService.findById(userId).get();
        String interests = user.getInterestsAndHobbies();
        String cities = user.getSuitableCities();
        String recommendation = openAIService.generateFaculties(interests, cities);
        userService.updateRecommendation(userId, recommendation);
        return ResponseEntity.ok(recommendation);
    }

    @PutMapping("/{id}/interests")
    public ResponseEntity<UserDto> updateInterestsAndHobbies(@PathVariable Integer id, @RequestBody String interestsAndHobbies) {
        userService.updateInterestsAndHobbies(id, interestsAndHobbies);
        return ResponseEntity.ok(mapper(userService.findById(id).get()));
    }
    @PutMapping("/{id}/cities")
    public ResponseEntity<UserDto> updateCities(@PathVariable Integer id, @RequestBody String cities) {
        userService.updateCities(id, cities);
        return ResponseEntity.ok(mapper(userService.findById(id).get()));
    }

    @PostMapping("/signup")
    @RateLimited(
        maximumRequests = 5,
        windowSize = 60,
        key = IPExtractor.class
    )
    public ResponseEntity<UserDto> signup(@RequestBody UserEntity user) {
        return ResponseEntity.ok(mapper(userService.save(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserEntity user) {
        return userService.findById(id)
                .map(existingUser -> {
                    user.setId(existingUser.getId());
                    UserEntity updatedUser = userService.save(user);
                    return ResponseEntity.ok(mapper(updatedUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok("User with ID " + id + " has been deleted.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("User with ID " + id + " not found.");
        }
    }
}
