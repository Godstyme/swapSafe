package com.example.swapSafe.service;

import com.example.swapSafe.dto.LoginRequest;
import com.example.swapSafe.dto.LoginResponse;
import com.example.swapSafe.dto.RegisterRequest;
import com.example.swapSafe.dto.RegisterResponse;
import com.example.swapSafe.model.User;
import com.example.swapSafe.repository.UserRepository;
import com.example.swapSafe.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthenticationService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    @Override
    public RegisterResponse registerCustomer(RegisterRequest req) {

        if (repo.existsByEmail(req.email())) {
            throw new RuntimeException("Email taken");
        }
        BigDecimal initialBalance = BigDecimal.ZERO;
        User user = User.builder()
                .name(req.name())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .roles(Set.of(User.Role.CUSTOMER))
                .balance(initialBalance)
                .build();

        repo.save(user);

        return new RegisterResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        if (req.password() == null || req.password().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Find the user by email
        User user = repo.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Check if password matches
        if (!encoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT token
        String token = jwt.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRoles().stream().map(Enum::name).collect(Collectors.toSet())
        );

        return new LoginResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRoles());
    }
}
