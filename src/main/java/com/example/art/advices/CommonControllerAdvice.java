package com.example.art.advices;

import com.example.art.dto.BaseResponse;
import com.example.art.exceptions.MissingUserRequestParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class CommonControllerAdvice {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handleMissingRequestParameterException(MissingServletRequestParameterException exception){
        log.error("Missing request parameter: {}", exception.getParameterName());
        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg("Missing request parameter: "+exception.getParameterName())
                .build();
    }

    @ExceptionHandler(MissingUserRequestParamException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handleMissingRequestParamException(MissingUserRequestParamException exception){
        log.error(exception.getCustomMsg());
        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg(exception.getCustomMsg())
                .build();
    }

}
