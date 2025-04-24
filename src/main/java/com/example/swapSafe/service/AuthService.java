package com.example.swapSafe.service;

import com.example.swapSafe.dto.RegisterRequest;
import com.example.swapSafe.dto.RegisterResponse;
import com.example.swapSafe.model.User;
import com.example.swapSafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public RegisterResponse registerCustomer(RegisterRequest req) {
        if (repo.existsByEmail(req.email())) throw new RuntimeException("Email taken");
        User user = User.builder()
                .name(req.name())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .roles(Set.of(User.Role.CUSTOMER))
                .build();
        repo.save(user);
        return new RegisterResponse(user.getId(), user.getName(), user.getEmail());
    }
}
