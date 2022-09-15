package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.User;
import com.poseidon.webapp.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    public String getCurrentUserDetailsUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof org.springframework.security.core.userdetails.User) {
                return ((org.springframework.security.core.userdetails.User) principal).getUsername();
            }
        }
        return null;
    }

    public User getCurrentUser() {
        return userRepository.findByUsername(getCurrentUserDetailsUserName());
    }
}
