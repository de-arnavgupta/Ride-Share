package com.saniya.ride_share.service;

import com.saniya.ride_share.dto.AuthDtos;

public interface AuthService {

    AuthDtos.AuthResponse register(AuthDtos.RegisterRequest request);

    AuthDtos.AuthResponse login(AuthDtos.LoginRequest request);
}

