package com.example.art.advices;

import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.BusinessException;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.MissingUserRequestParamException;
import com.example.art.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CommonControllerAdvice {

    @ExceptionHandler({AuthenticationException.class,BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse handleException(BadCredentialsException ex){
        log.error("authentication error: {}", ex.getMessage());
        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg(ex.getMessage())
                .responseCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .build();
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse handleMissingRequestParameterException(MissingServletRequestParameterException exception){
        log.error("Missing request parameter: {}", exception.getParameterName());
        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg("Missing request parameter: "+exception.getParameterName())
                .build();
    }

    @ExceptionHandler({MissingUserRequestParamException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse handleMissingRequestParamException(MissingUserRequestParamException exception){
        log.error(exception.getCustomMsg());
        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg(exception.getCustomMsg())
                .build();
    }


    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse handleBusinessExceptions(BusinessException exception){
        log.error(exception.getCustomMsg());
        return BaseResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .responseMsg(exception.getCustomMsg())
                .data(exception.getErrors())
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
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

    @ExceptionHandler({EntityNotFoundException.class, UserNotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handleUserNotFoundException(Exception ex){
        log.error(ex.getMessage());
        return BaseResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .responseMsg(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse handleException(Exception ex){
        log.error(ex.getMessage());
        ex.printStackTrace();
        return BaseResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .responseMsg(ex.getMessage())
                .build();
    }

}
