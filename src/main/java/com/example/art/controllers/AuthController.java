package com.example.art.controllers;

import com.example.art.dto.request.CreateUserRequest;
import com.example.art.dto.request.LoginRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.services.AuthService;
import com.example.art.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public BaseResponse loginUser(@RequestBody LoginRequest request) throws AuthenticationException {
        return authService.loginUser(request);
    }

    @PostMapping("/register")
    public BaseResponse registerUser(@RequestBody CreateUserRequest request) throws DuplicateEntryException {
        return userService.addNewUser(request);
    }

}
