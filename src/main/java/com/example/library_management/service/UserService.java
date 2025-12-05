package com.example.library_management.service;

import com.example.library_management.model.User;
import com.example.library_management.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to validate login safely
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            if (user.getRole() == null) {
                user.setRole("USER"); // default role if null
            }
            return user;
        }
        return null;
    }
}
