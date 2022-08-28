package com.example.art.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdatePartyRequest {

    @NotEmpty
    private String partyName;

    private String address;

    private String authority;

    private String mobile;

    private String email;

    private Boolean isActive;

}
