package com.example.art.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateDealRequest {

    @NotEmpty
    private String partyName;

    @NotEmpty
    private String dealName;

}
