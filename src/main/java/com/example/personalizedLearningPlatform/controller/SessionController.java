package com.example.personalizedLearningPlatform.controller;

import com.example.personalizedLearningPlatform.dto.SessionDto;
import com.example.personalizedLearningPlatform.dto.mapper.Mapper;
import com.example.personalizedLearningPlatform.entity.SessionEntity;
import com.example.personalizedLearningPlatform.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.personalizedLearningPlatform.dto.mapper.Mapper.mapper;

@RestController
@AllArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @GetMapping("/sessions1/{key}")
    public ResponseEntity<SessionDto> getSession(@PathVariable(name = "key") String key) {
        Optional<SessionEntity> sessionEntity = sessionService.getSessionBySessionKey(key);
        if (sessionEntity.isPresent()) {
            return ResponseEntity.ok(mapper(sessionEntity.get()));
        }
        else return ResponseEntity.notFound().build();
    }
    @PostMapping("/sessions")
    public ResponseEntity<SessionDto> createSession(@RequestBody String key) {
        Optional<SessionEntity> sessionEntity = sessionService.create(key);
        if (sessionEntity.isPresent()) {
            return ResponseEntity.ok(mapper(sessionEntity.get()));
        }
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<SessionDto>> getSessions(@RequestParam(name = "userId", required = false) Long userId,
                                                     @RequestParam(name = "isActive", required = false) Boolean isActive) {
       List<SessionEntity> sessionEntities = sessionService.find(userId,isActive);
       return ResponseEntity.ok(sessionEntities.stream().map(sessionEntity ->mapper(sessionEntity)).collect(Collectors.toList()));


    }

    @DeleteMapping("/sessions/{key}")
    public ResponseEntity<Boolean> deleteSession(@PathVariable(name = "key") String key) {
        sessionService.delete(key);
        return ResponseEntity.ok(true);
    }
}
