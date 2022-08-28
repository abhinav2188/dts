package com.example.art.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Builder
@Data
@ToString
public class BaseResponse<T> {

    private String status;

    private int statusCode;

    private String responseMsg;

    private String responseCode;

    private T data;

    public static class BaseResponseBuilder<T> {

        private String status;
        private int statusCode;

        public BaseResponseBuilder<T> status(HttpStatus httpStatus){
            this.status = httpStatus.is2xxSuccessful() ? "SUCCESS" : "FAILURE";
            this.statusCode = httpStatus.value();
            return this;
        }

    }

}

