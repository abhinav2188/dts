package com.example.art.services;

import com.example.art.dto.BaseResponse;
import com.example.art.dto.request.CreateUserRequest;
import com.example.art.exceptions.DuplicateEntryException;

public interface UserService {

    BaseResponse addNewUser(CreateUserRequest createUserRequest) throws DuplicateEntryException;

}
