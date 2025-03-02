package com.example.personalizedLearningPlatform.web;

import com.example.personalizedLearningPlatform.repo.UserActivityRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserActivityInterceptor implements HandlerInterceptor {
    private final UserActivityRepository userActivityRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("Auth object: {}", auth);  // Debugging Authentication Object

        String email = "anonymous";
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            Object principal = auth.getPrincipal();
            log.info("Principal: {}", principal);

            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
            } else {
                email = principal.toString();
            }
        }

        String action = request.getMethod();
        String endpoint = request.getRequestURI();
        log.info("Saving user activity: email={}, action={}, endpoint={}", email, action, endpoint);
        userActivityRepository.saveUserActivity(email, action, endpoint);

        return true;
    }

}