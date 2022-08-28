package com.example.art.dto.response;

import lombok.Data;

@Data
public class PartyResponse {

    // for app response

    private Long id;

    private String partyName;

    private String address;

    private String authority;

    private String mobile;

    private String email;

    private Boolean isActive;

}
