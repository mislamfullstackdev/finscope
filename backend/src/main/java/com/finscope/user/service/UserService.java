package com.finscope.user.service;

import com.finscope.user.dto.RegisterRequest;
import com.finscope.user.dto.RegisterResponse;
import com.finscope.user.entity.User;
import com.finscope.user.repository.UserRepository;

import org.springframework.stereotype.Service;

//password encryption
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, 
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse registerUser(RegisterRequest request) {
        // Check if the email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Create a new User entity
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Return a response DTO
        return new RegisterResponse(savedUser.getId(), savedUser.getEmail());
    }
}
