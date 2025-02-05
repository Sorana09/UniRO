package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.exception.AlreadyUserExistException;
import com.example.personalizedLearningPlatform.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.personalizedLearningPlatform.crypt.MD5.getMD5;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean verifyPassword(Integer Id, String password) {
        Optional<UserEntity> user = userRepository.findById(Id);
        return user.map(it -> it.getHashedPassword().equals(getMD5(password))).orElse(false);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity save(UserEntity user) {
        if (findByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyUserExistException();
        }
        user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
        user.setIsAdmin(Boolean.FALSE);
        userRepository.save(user);
        log.info("creating entity {}", user);
        return user;
    }


    private Optional<UserEntity> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
