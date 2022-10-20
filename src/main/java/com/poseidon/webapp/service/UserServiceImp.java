package com.poseidon.webapp.service;

import com.poseidon.webapp.domain.User;
import com.poseidon.webapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp {
    UserRepository userRepository;

    public Boolean existsByUserName(String userName) {
        return userRepository.existsByUsername(userName);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }
}
