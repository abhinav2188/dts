package com.example.art.services.impl;

import com.example.art.dto.BaseResponse;
import com.example.art.dto.request.CreateUserRequest;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.model.User;
import com.example.art.repository.UserRepository;
import com.example.art.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public BaseResponse addNewUser(CreateUserRequest createUserRequest) throws DuplicateEntryException {

        validateCreateUserRequest(createUserRequest);

        User user = userRepository.save(new User(createUserRequest));

        return BaseResponse.builder()
                .status(HttpStatus.CREATED)
                .responseCode(String.valueOf(HttpStatus.CREATED.value()))
                .responseMsg("new user saved")
                .build();
    }

    private void validateCreateUserRequest(CreateUserRequest createUserRequest) throws DuplicateEntryException {

        List<String> duplicateFields = new ArrayList<>();
        if(userRepository.existsByEmail(createUserRequest.getEmail())){
            duplicateFields.add("Email");
        }
        if(userRepository.existsByUsername(createUserRequest.getUsername())){
            duplicateFields.add("Username");
        }
        if(userRepository.existsByMobile(createUserRequest.getMobile())){
            duplicateFields.add("Mobile");
        }
        if(duplicateFields.size() > 0){
            throw new DuplicateEntryException(duplicateFields);
        }
    }

}
