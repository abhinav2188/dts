package com.example.art.dto.request;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UpdateUserAuthorizationRequest {

    private String designation;

    private Boolean isActive;

    private String roles;

}
