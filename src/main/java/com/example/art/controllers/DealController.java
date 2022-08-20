package com.example.art.controllers;

import com.example.art.dto.request.CreateDealRequest;
import com.example.art.dto.request.UpdateDealSection3Request;
import com.example.art.dto.request.UpdateDealSection4Request;
import com.example.art.dto.request.UpdateProductDetailsRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.DealDetailResponse2;
import com.example.art.dto.response.MultipleDealsResponse;
import com.example.art.dto.response.inner.DealCardDetails;
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
    public BaseResponse<DealCardDetails> createDeal(@RequestBody CreateDealRequest requestDto)
            throws InvalidFieldException, DuplicateEntryException {
        return dealService.createDeal(requestDto);
    }

    @PatchMapping("/{dealId}/s2")
    public BaseResponse updateProductDetails(@PathVariable Long dealId,
                                             @RequestBody UpdateProductDetailsRequest requestDto)
            throws NoAuthorizationException, EntityNotFoundException {
        return dealService.updateProductDetails(dealId, requestDto);
    }

    @PatchMapping("/{dealId}/s3")
    public BaseResponse updateDealDetails(@PathVariable Long dealId,
                                          @RequestBody UpdateDealSection3Request requestDto)
            throws NoAuthorizationException, EntityNotFoundException {
        return dealService.updateDealSection3(dealId, requestDto);
    }

    @PatchMapping("/{dealId}/s4")
    public BaseResponse updateDealSection4(@PathVariable Long dealId,
                                           @RequestBody UpdateDealSection4Request requestDto)
            throws NoAuthorizationException, EntityNotFoundException {
        return dealService.updateDealSection4(dealId, requestDto);
    }

    @GetMapping("/all")
    public BaseResponse<MultipleDealsResponse> getDeals(@RequestParam(name = "pageNo") int pageNo,
                                                        @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize,
                                                        @RequestParam(name="dealName", required = false) String dealName,
                                                        @RequestParam(name="partyName", required = false) String partyName,
                                                        @RequestParam(name="coOwnerEmail", required = false) String coOwnerEmail) {
        return dealService.getMultipleDeals(pageNo,pageSize,dealName,partyName,coOwnerEmail);
    }

    @GetMapping("/{dealId}")
    public BaseResponse<DealDetailResponse2> getDealDetails(@PathVariable Long dealId,
                                                            @RequestParam(name = "userId") Long userId)
            throws NoAuthorizationException, EntityNotFoundException {
        return dealService.getDealDetails2(dealId,userId);
    }

}
