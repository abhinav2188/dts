package com.example.art.dto.mapper;

import com.example.art.dto.request.CreateUserRequest;
import com.example.art.dto.request.UpdateUserAuthorizationRequest;
import com.example.art.dto.request.UpdateUserProfileRequest;
import com.example.art.model.User;
import com.example.art.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserMapper {

    @Autowired
    private StringUtils stringUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(CreateUserRequest createUserRequest){
        User user = new User();
        user.setMobile(createUserRequest.getMobile());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setIsActive(true);
        return user;
    }

    public void updateUser(User user, UpdateUserAuthorizationRequest request) {
        List<String> updateMsgs = new ArrayList<>();
        if(request.getIsActive()!=null && !request.getIsActive().equals(user.getIsActive())){
            updateMsgs.add(stringUtils.generateUpdateMsg("isActive",user.getIsActive(), request.getIsActive()));
            user.setIsActive(request.getIsActive());
        }
        if(request.getRoles()!=null && !request.getRoles().equals(user.getRoles())){
            updateMsgs.add(stringUtils.generateUpdateMsg("roles",user.getRoles(), request.getRoles()));
            user.setRoles(request.getRoles());
        }
        if(request.getDesignation()!=null && !request.getDesignation().equals(user.getDesignation())){
            updateMsgs.add(stringUtils.generateUpdateMsg("designation",user.getDesignation(), request.getDesignation()));
            user.setDesignation(request.getDesignation());
        }
        log.info("Updated User with id= "+ user.getId() + " :" + updateMsgs.toString());
    }

    public void updateUser(User user, UpdateUserProfileRequest request) {
        List<String> updateMsgs = new ArrayList<>();
        if(request.getMobile()!=null && !request.getMobile().equals(user.getMobile())){
            updateMsgs.add(stringUtils.generateUpdateMsg("mobile",user.getMobile(), request.getMobile()));
            user.setMobile(request.getMobile());
        }
        if(request.getEmail()!=null && !request.getEmail().equals(user.getEmail())){
            updateMsgs.add(stringUtils.generateUpdateMsg("email",user.getEmail(), request.getEmail()));
            user.setRoles(request.getEmail());
        }
        log.info("Updated User with id= "+ user.getId() + " :" + updateMsgs.toString());
    }

}
