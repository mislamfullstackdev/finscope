package com.finscope.user.service;

import com.finscope.common.exception.EmailAlreadyExistsException;
import com.finscope.common.exception.InvalidCredentialsException;
import com.finscope.user.dto.RegisterRequest;
import com.finscope.user.dto.RegisterResponse;
import com.finscope.user.entity.User;
import com.finscope.user.repository.UserRepository;

import com.finscope.security.JwtService;
import com.finscope.user.dto.LoginRequest;
import com.finscope.user.dto.LoginResponse;

import org.springframework.stereotype.Service;

//password encryption
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, 
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;   
    }

    public RegisterResponse registerUser(RegisterRequest request) {
        // Check if the email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
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

    public LoginResponse login(LoginRequest request) {
        // Find the user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        // Check if the password matches
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Generate a JWT token
        String token = jwtService.generateToken(user.getEmail());

        // Return a response DTO with the token
        return new LoginResponse(token);
    }
}
