package com.example.art.controllers.internal;

import com.example.art.dto.request.UpdateDealAuthorizationRequest;
import com.example.art.dto.request.UpdateDealSection4Request;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.services.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{dealId}/auth")
    public BaseResponse updateDealAuthorization(@PathVariable Long dealId,
                                                @RequestBody UpdateDealAuthorizationRequest request)
            throws EntityNotFoundException {
        return dealService.updateDealAuthorization(dealId, request);
    }
}
