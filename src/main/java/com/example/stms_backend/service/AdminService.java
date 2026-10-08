package com.example.stms_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.stms_backend.entity.User;
import com.example.stms_backend.repository.UserRepository;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public User updateUserRole(Long id, String role) {
        User user = getUserById(id);

        user.setRole(role.toUpperCase());

        return userRepository.save(user);
    }
}