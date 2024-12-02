package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.exception.EntityNotFoundException;
import com.example.personalizedLearningPlatform.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.personalizedLearningPlatform.crypt.MD5.getMD5;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserEntity changePassword(String email, String newPassword) {
        UserEntity user = this.getByEmail(email).orElseThrow(() -> new EntityNotFoundException());
        user.setHashed_password(getMD5(newPassword));

        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }


    private Optional<UserEntity> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
