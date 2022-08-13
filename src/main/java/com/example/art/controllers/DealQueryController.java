package com.example.art.controllers;

import com.example.art.dto.request.DealQueryRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DealQueryController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/api/deal-query")
    public BaseResponse sendDealQueryEmail(@RequestBody DealQueryRequest request){
        return emailService.sendSimpleMail(request);
    }

}
