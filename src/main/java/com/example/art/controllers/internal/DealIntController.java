package com.example.art.controllers.internal;

import com.example.art.dto.request.UpdateDealAuthorizationRequest;
import com.example.art.dto.request.UpdateDealOwnerRequest;
import com.example.art.dto.request.UpdateDealSection4Request;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.DealDetailResponse;
import com.example.art.dto.response.MultipleDealsResponse;
import com.example.art.dto.response.inner.DealUserDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.InvalidOperationException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.exceptions.UserNotFoundException;
import com.example.art.services.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/int/deals")
public class DealIntController {

    @Autowired
    private DealService dealService;

    @PatchMapping("/{dealId}/s4")
    public BaseResponse updateDealSection4(@PathVariable Long dealId,
                                             @RequestBody UpdateDealSection4Request requestDto)
            throws NoAuthorizationException, EntityNotFoundException {
        return dealService.updateDealSection4(dealId, requestDto);
    }

    @GetMapping("/{dealId}")
    public BaseResponse<DealDetailResponse> getDealDetails(@PathVariable Long dealId,
                                                           @RequestParam(name = "userId", required = false) Long userId)
            throws NoAuthorizationException, EntityNotFoundException {
        return dealService.getDealDetails(dealId,userId);
    }

    @PatchMapping("/{dealId}/add-auth")
    public BaseResponse<DealUserDetails> addDealOwner(@PathVariable Long dealId,
                                                      @Valid @RequestBody UpdateDealOwnerRequest request)
            throws NoAuthorizationException, EntityNotFoundException, InvalidOperationException {
        return dealService.addDealOwner(dealId, request.getEmail());
    }

    @PatchMapping("/{dealId}/remove-auth")
    public BaseResponse<DealUserDetails> removeDealOwner(@PathVariable Long dealId,
                                                         @Valid @RequestBody UpdateDealOwnerRequest request)
            throws NoAuthorizationException, EntityNotFoundException, InvalidOperationException {
        return dealService.removeDealOwner(dealId, request.getEmail());
    }

    // not used until
    @PatchMapping("/{dealId}/auth")
    public BaseResponse updateDealAuthorization(@PathVariable Long dealId,
                                                @RequestBody UpdateDealAuthorizationRequest request)
            throws EntityNotFoundException {
        return dealService.updateDealAuthorization(dealId, request);
    }

    @GetMapping("/all")
    public BaseResponse<MultipleDealsResponse> getDeals(@RequestParam(name = "pageNo") int pageNo,
                                                        @RequestParam(name = "pageSize", required = false, defaultValue = "15") int pageSize,
                                                        @RequestParam(name="dealName", required = false) String dealName,
                                                        @RequestParam(name="partyName", required = false) String partyName,
                                                        @RequestParam(name="coOwnerEmail", required = false) String coOwnerEmail) {
        return dealService.getMultipleDeals(pageNo,pageSize,dealName,partyName,coOwnerEmail);
    }

    @DeleteMapping("/{dealId}")
    public BaseResponse deleteDeal(@PathVariable Long dealId) throws NoAuthorizationException, EntityNotFoundException {
        return dealService.deleteDeal(dealId);
    }


}
