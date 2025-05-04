package com.example.swapSafe.dto;

public record LoginResponse(String token,
                            Long   id,
                            String name,
                            String email,
                            java.util.Set<com.example.swapSafe.model.User.Role> roles) {

}