package com.shankarstudy.todo.service;

import com.shankarstudy.todo.dto.JwtAuthResponse;
import com.shankarstudy.todo.dto.LoginDto;
import com.shankarstudy.todo.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
}
