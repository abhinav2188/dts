package com.example.art.dto.response;

import com.example.art.model.User;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String email;

    private String mobile;

    private String designation;

    private String roles;

    public UserResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.designation = user.getDesignation();
        this.roles = user.getRoles();
    }
}
