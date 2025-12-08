package com.arnavgpt.ride_share.service;

import com.arnavgpt.ride_share.dto.AuthDtos;

public interface AuthService {

    AuthDtos.AuthResponse register(AuthDtos.RegisterRequest request);

    AuthDtos.AuthResponse login(AuthDtos.LoginRequest request);
}

