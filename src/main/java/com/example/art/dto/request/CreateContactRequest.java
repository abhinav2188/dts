package com.example.art.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateContactRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email1;

    private String email2;

    @NotEmpty
    private String mobile1;

    private String mobile2;

    private String designation;

}
