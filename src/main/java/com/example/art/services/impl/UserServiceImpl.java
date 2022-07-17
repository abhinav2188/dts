package com.example.art.services.impl;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.mapper.UserMapper;
import com.example.art.dto.request.CreateUserRequest;
import com.example.art.dto.request.UpdateUserAuthorizationRequest;
import com.example.art.dto.request.UpdateUserProfileRequest;
import com.example.art.dto.response.inner.UserCardDetails;
import com.example.art.dto.response.UserResponse;
import com.example.art.dto.response.UsersResponse;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.exceptions.UserNotFoundException;
import com.example.art.model.User;
import com.example.art.repository.UserRepository;
import com.example.art.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseResponse addNewUser(CreateUserRequest createUserRequest) throws DuplicateEntryException {

        validateCreateUserRequest(createUserRequest);

        User user = userRepository.save(new User(createUserRequest));

        log.info("new user added with email: {}",user.getEmail());

        return BaseResponse.builder()
                .status(HttpStatus.CREATED)
                .responseCode(String.valueOf(HttpStatus.CREATED.value()))
                .responseMsg("new user saved")
                .build();
    }

    @Override
    public BaseResponse<UsersResponse> getAllUsers(int pageNo, int pageSize) {

        UsersResponse response = new UsersResponse();

        Pageable pages = PageRequest.of(pageNo,pageSize, Sort.by("createTimestamp").descending());
        Page<User> users = userRepository.findAll(pages);

        log.info(users.toString());

        response.setTotalUsers(users.getTotalElements());
        response.setTotalPages(users.getTotalPages());

        List<UserCardDetails> usersList = users.stream().map(user-> new UserCardDetails(user)).collect(Collectors.toList());
        response.setUsersList(usersList);

        log.info("fetched users page no. {}, number of users = {}", pageNo, usersList.size());

        return BaseResponse.<UsersResponse>builder()
                .responseMsg("fetched "+usersList.size()+" users")
                .data(response)
                .status(HttpStatus.OK)
                .build();

    }

    @Override
    public BaseResponse<UserResponse> getUser(Long id) throws UserNotFoundException {
        UserResponse response = userRepository.findById(id).map(user -> new UserResponse(user))
                .orElseThrow(() -> new UserNotFoundException("id",id));
        return BaseResponse.<UserResponse>builder()
                .status(HttpStatus.OK)
                .data(response)
                .build();
    }

    @Override
    public BaseResponse updateUserAuthorization(Long id, UpdateUserAuthorizationRequest requestBody) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow( () -> new UserNotFoundException("id",id));
        userMapper.updateUser(user, requestBody);
        userRepository.save(user);

        return BaseResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .responseMsg("updated user authorization")
                .build();
    }

    @Override
    public BaseResponse updateUserProfile(Long id, UpdateUserProfileRequest requestBody) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow( () -> new UserNotFoundException("id",id));
        userMapper.updateUser(user, requestBody);
        userRepository.save(user);

        return BaseResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .responseMsg("updated user profile details")
                .build();
    }

    private void validateCreateUserRequest(CreateUserRequest createUserRequest) throws DuplicateEntryException {

        List<String> duplicateFields = new ArrayList<>();
        if(userRepository.existsByEmail(createUserRequest.getEmail())){
            duplicateFields.add("Email");
        }
        if(userRepository.existsByMobile(createUserRequest.getMobile())){
            duplicateFields.add("Mobile");
        }
        if(duplicateFields.size() > 0){
            throw new DuplicateEntryException(duplicateFields);
        }
    }

}
