package com.example.art.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequest {

    @NotEmpty
    @Pattern( regexp ="^[a-zA-Z0-9._]+@[a-zA-Z0-9._]+\\.[a-zA-Z]{2,6}$",
    message = "email format is not valid")
    private String email;

    @NotEmpty
    @Pattern(regexp = "[0-9]{10}", message = "mobile format is not valid, must contains 10 digits")
    private String mobile;

    @NotEmpty
    @Size(min=8, max=20, message = "password should have at least 8 characters, at most 20 characters")
    private String password;

}
