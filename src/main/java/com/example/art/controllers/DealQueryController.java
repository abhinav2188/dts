package com.example.art.controllers;

import com.example.art.dto.request.DealQueryRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DealQueryController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/api/deal-query/{dealId}")
    public BaseResponse sendDealQueryEmail(@RequestBody DealQueryRequest request, @PathVariable Long dealId)
            throws NoAuthorizationException, EntityNotFoundException {
        return emailService.sendDealQueryMail(dealId,request);
    }

}
