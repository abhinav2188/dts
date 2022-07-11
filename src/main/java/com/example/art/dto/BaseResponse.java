package com.example.art.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class BaseResponse<T> {

    private HttpStatus status;

    private String responseMsg;

    private String responseCode;

    private T data;

}
