package com.example.swapSafe.controller;

import com.example.swapSafe.dto.LoginRequest;
import com.example.swapSafe.dto.LoginResponse;
import com.example.swapSafe.dto.RegisterRequest;
import com.example.swapSafe.dto.RegisterResponse;
import com.example.swapSafe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService auth;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest req){
        System.out.println("Name: " + req.name());
        return ResponseEntity.ok(auth.registerCustomer(req));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req){
        return ResponseEntity.ok(auth.login(req));
    }

}

