package com.example.swapSafe.service;

import com.example.swapSafe.dto.LoginRequest;
import com.example.swapSafe.dto.LoginResponse;
import com.example.swapSafe.dto.RegisterRequest;
import com.example.swapSafe.dto.RegisterResponse;

public interface AuthenticationService {
    RegisterResponse registerCustomer(RegisterRequest req);
    LoginResponse login(LoginRequest req);
}
