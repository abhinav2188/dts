package com.example.art.dto.response.inner;

import lombok.Data;

@Data
public class PartyDetails {

    private Long id;

    private String partyName;

    private String address;

    private String authority;

    private String mobile;

    private String email;

    private Boolean isActive;

    private int dealsCount;

}
