package com.example.art.services;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.request.CreateUserRequest;
import com.example.art.dto.request.UpdateUserAuthorizationRequest;
import com.example.art.dto.request.UpdateUserProfileRequest;
import com.example.art.dto.response.UserResponse;
import com.example.art.dto.response.UsersResponse;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.exceptions.UserNotFoundException;

public interface UserService {

    BaseResponse addNewUser(CreateUserRequest createUserRequest) throws DuplicateEntryException;

    BaseResponse<UsersResponse> getAllUsers(int pageNo, int pageSize);

    BaseResponse<UserResponse> getUser(Long id) throws UserNotFoundException;

    BaseResponse updateUserAuthorization(Long id, UpdateUserAuthorizationRequest requestBody) throws UserNotFoundException;

    BaseResponse updateUserProfile(Long id, UpdateUserProfileRequest requestBody) throws UserNotFoundException;
}
