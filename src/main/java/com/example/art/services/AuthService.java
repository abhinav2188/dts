package com.example.art.services;


import com.example.art.dto.request.CreateUserRequest;
import com.example.art.dto.request.LoginRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.LoginResponse;

public interface AuthService {

    BaseResponse<LoginResponse> loginUser(LoginRequest requestDto);

    BaseResponse registerUser(CreateUserRequest request);

}
