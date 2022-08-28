package com.example.art.controllers;

import com.example.art.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidateController {

    @GetMapping("/api/token")
    public BaseResponse isTokenValid(){
        return BaseResponse.builder()
                .status(HttpStatus.OK)
                .build();
    }

}
