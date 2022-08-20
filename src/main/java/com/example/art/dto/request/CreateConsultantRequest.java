package com.example.art.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateConsultantRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String mobile;

    private String designation;

}
