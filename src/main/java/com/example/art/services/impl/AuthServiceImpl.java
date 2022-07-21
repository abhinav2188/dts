package com.example.art.services.impl;


import com.example.art.dto.request.CreateUserRequest;
import com.example.art.dto.request.LoginRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.LoginResponse;
import com.example.art.services.AuthService;
import com.example.art.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public BaseResponse<LoginResponse> loginUser(LoginRequest requestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(),requestDto.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        log.info("userDetails: {}",userDetails);

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        return BaseResponse.<LoginResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg("user logged in with username= "+userDetails.getUsername())
                .data(response)
                .build();

    }

    @Override
    public BaseResponse registerUser(CreateUserRequest request) {
        return null;
    }
}
