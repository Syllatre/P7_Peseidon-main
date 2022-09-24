package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.User;
import com.poseidon.webapp.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Boolean existsByUserName(String userName) {
        return userRepository.existsByUsername(userName);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User create(User user){
        return userRepository.save(user);
    }
    public User getCurrentUser() {
        return userRepository.findByUsername(getCurrentUserDetailsUserName());
    }
}
