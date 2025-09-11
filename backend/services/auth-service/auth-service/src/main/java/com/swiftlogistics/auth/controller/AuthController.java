package com.swiftlogistics.auth.controller;

import com.swiftlogistics.auth.dto.AuthRequest;
import com.swiftlogistics.auth.dto.AuthResponse;
import com.swiftlogistics.auth.dto.RegisterRequest;
import com.swiftlogistics.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")

    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request,
            @RequestHeader(value = "X-Client-Source", required = false) String clientSource) {

        if (clientSource == null) {
            clientSource = "web";
        }

        AuthResponse response = authService.register(request, clientSource);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        return authService.authenticate(request);
    }
}
