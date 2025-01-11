package com.example.personalizedLearningPlatform.service;

import com.example.personalizedLearningPlatform.entity.SessionEntity;
import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.exception.EntityNotFoundException;
import com.example.personalizedLearningPlatform.exception.TooManySeesionException;
import com.example.personalizedLearningPlatform.exception.WrongPasswordException;
import com.example.personalizedLearningPlatform.repo.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SessionService{

    private final SessionRepository sessionRepository;
    private final UserService userService;

    public static boolean isExpired(SessionEntity session) {
        return session.getExpiredAt().isBefore(OffsetDateTime.now(ZoneOffset.UTC));
    }
    public List<SessionEntity> find(Long userId,Boolean active){
        List<SessionEntity> sessionEntity = sessionRepository.find(userId);
        if(!active) {
            return sessionEntity;
        }
        if(active){
            return sessionEntity.stream().filter(sessionEntity1 -> !isExpired(sessionEntity1)).collect(Collectors.toList());

        }
        return sessionEntity.stream().filter(SessionService::isExpired).collect(Collectors.toList());
    }

    public Optional<SessionEntity> create(String key){
        Optional<UserEntity> userEntity = userService.findByEmail(key);
        if(userEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }

        UserEntity user = userEntity.get();
        if(!userService.verifyPassword(user.getId(),user.getHashedPassword())){
            throw new WrongPasswordException();
        }

        if(find(user.getId(), true).size() >=3){
            throw new TooManySeesionException();
        }

        SessionEntity sessionEntity = new SessionEntity();
        UUID sessionkey  = UUID.randomUUID();
        sessionEntity.setSessionKey((sessionkey).toString());
        sessionEntity.setUserId(user.getId());
        sessionEntity.setExpiredAt(OffsetDateTime.now(ZoneOffset.UTC).plusDays(30));

        sessionRepository.insert(sessionEntity);
        return Optional.of(sessionEntity);
    }

    public Optional<SessionEntity> getSessionBySessionKey(String sessionKey){
        Optional<SessionEntity> sessionEntity = sessionRepository.getByKey(sessionKey);
        if(sessionEntity.isEmpty()) { return Optional.empty(); }
        if(!isExpired(sessionEntity.get())) { return Optional.empty(); }
        return sessionEntity;
    }


    public void delete(String key){
        Optional<SessionEntity> sessionEntity = sessionRepository.getByKey(key);
        if(sessionEntity.isEmpty()) {
            return;
        }
        sessionRepository.deleteByKey(key);

    }

}
