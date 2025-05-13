package com.example.swapSafe.controller;

import com.example.swapSafe.dto.LoginRequest;
import com.example.swapSafe.dto.LoginResponse;
import com.example.swapSafe.dto.RegisterRequest;
import com.example.swapSafe.dto.RegisterResponse;
import com.example.swapSafe.model.User;
import com.example.swapSafe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173/")
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

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return auth.getAllUsers();
    }

}

