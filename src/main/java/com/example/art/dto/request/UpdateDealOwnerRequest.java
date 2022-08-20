package com.example.art.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateDealOwnerRequest {
    @NotEmpty
    private String email;
}
