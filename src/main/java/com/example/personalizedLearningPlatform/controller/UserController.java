package com.example.personalizedLearningPlatform.controller;

import com.example.personalizedLearningPlatform.dto.UserDto;
import com.example.personalizedLearningPlatform.dto.mapper.Mapper;
import com.example.personalizedLearningPlatform.dto.request.ChangePasswordRequest;
import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.exception.EntityNotFoundException;
import com.example.personalizedLearningPlatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.personalizedLearningPlatform.dto.mapper.Mapper.mapper;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(user -> ResponseEntity.ok(mapper(user)))
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(user -> ResponseEntity.ok(mapper(user)))
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> users = userService.findAll();

        List<UserDto> userDtos = users.stream()
                .map(it -> mapper(it))
                .collect(Collectors.toList());
        if(userDtos.size() > 0)
            return ResponseEntity.ok(userDtos);
        else return ResponseEntity.noContent().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserEntity user) {
        UserEntity savedUser = userService.save(user);
        return ResponseEntity.status(201).body("User created successfully with ID " + savedUser.getId());
    }

    @PutMapping("/resetPass/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable String email,
                                                @RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            userService.changePassword(email, changePasswordRequest.getPassword());
            return ResponseEntity.ok("Password changed successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("User with email " + email + " not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
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
