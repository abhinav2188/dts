package com.example.art.controllers;

import com.example.art.dto.BaseResponse;
import com.example.art.dto.request.CreateUserRequest;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.model.User;
import com.example.art.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public BaseResponse addUser(@Valid @RequestBody CreateUserRequest createUserRequest) throws DuplicateEntryException {

        return userService.addNewUser(createUserRequest);

    }

}
