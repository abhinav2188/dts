package com.example.art.dto.response.inner;

import com.example.art.model.User;
import lombok.Data;

@Data
public class UserCardDetails {

    private Long id;

    private String email;

    private String mobile;

    private String designation;

    private Boolean isActive;

    private String roles;

    // todo : link for users deals list(owned, co-owned)

    public UserCardDetails(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.designation = user.getDesignation();
        this.isActive = user.getIsActive();
        this.roles = user.getRoles();
    }

}
