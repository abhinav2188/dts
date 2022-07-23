package com.example.art.controllers;

import com.example.art.dto.CreateDealResponse;
import com.example.art.dto.request.CreateDealRequest;
import com.example.art.dto.request.UpdateProductDetailsRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.InvalidFieldException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.services.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ext/deals")
public class DealController {

    @Autowired
    private DealService dealService;

    @PostMapping
    public BaseResponse<CreateDealResponse> createDeal(@RequestBody CreateDealRequest requestDto)
            throws InvalidFieldException, DuplicateEntryException {
        return dealService.createDeal(requestDto);
    }

    @PatchMapping("/{dealId}/s2")
    public BaseResponse updateProductDetails(@PathVariable Long dealId,
                                             @RequestBody UpdateProductDetailsRequest requestDto)
            throws NoAuthorizationException, EntityNotFoundException {
        return dealService.updateProductDetails(dealId, requestDto);
    }

}
