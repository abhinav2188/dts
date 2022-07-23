package com.example.art.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateDealRequest {

    @NotEmpty
    private Long userId;

    @NotEmpty
    private Long partyId;

    @NotEmpty
    private String dealName;

}
