package com.example.art.controllers;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.request.CreateUserRequest;
import com.example.art.dto.response.UserResponse;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.exceptions.UserNotFoundException;
import com.example.art.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ext/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public BaseResponse addUser(@Valid @RequestBody CreateUserRequest createUserRequest) throws DuplicateEntryException {
        return userService.addNewUser(createUserRequest);
    }

    @GetMapping("/{id}")
    public BaseResponse<UserResponse> getUser(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUser(id);
    }

}
