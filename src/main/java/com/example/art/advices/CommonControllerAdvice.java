package com.example.art.advices;

import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.BusinessException;
import com.example.art.exceptions.MissingUserRequestParamException;
import com.example.art.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler({MissingUserRequestParamException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handleMissingRequestParamException(MissingUserRequestParamException exception){
        log.error(exception.getCustomMsg());
        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg(exception.getCustomMsg())
                .build();
    }


    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handleBusinessExceptions(BusinessException exception){
        log.error(exception.getCustomMsg());
        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg(exception.getCustomMsg())
                .data(exception.getErrors())
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse handleInvalidRequestBodyException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        log.error("Invalid Request Body: "+errors.toString());
        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg("one or more fields are not valid")
                .data(errors)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleUserNotFoundException(UserNotFoundException ex){
        log.error(ex.getMessage());
        return BaseResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .responseMsg(ex.getMessage())
                .build();
    }


}
