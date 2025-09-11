package com.swiftlogistics.auth.service;

import com.swiftlogistics.auth.dto.AuthRequest;
import com.swiftlogistics.auth.dto.AuthResponse;
import com.swiftlogistics.auth.dto.RegisterRequest;
import com.swiftlogistics.auth.model.User;
import com.swiftlogistics.auth.model.Role;
import com.swiftlogistics.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request, String clientSource) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        if ("mobile".equalsIgnoreCase(clientSource)) {
            user.setRole(Role.DRIVER);
        } else {
            user.setRole(request.getRole());
        }

        userRepository.save(user);
        String token = jwtService.generateToken(user.getUserId());
        return new AuthResponse(token, user.getRole().name(),user.getUserId());
    }

    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getUserId());
        return new AuthResponse(token, user.getRole().name(),user.getUserId());
    }
}