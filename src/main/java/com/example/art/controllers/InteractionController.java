package com.example.art.controllers;

import com.example.art.dto.request.CreateInteractionRequest;
import com.example.art.dto.request.UpdateInteractionRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.InteractionsResponse;
import com.example.art.dto.response.inner.InteractionDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.services.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/ext/interactions")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    @PostMapping("/{dealId}")
    public BaseResponse<InteractionDetails> addDealInteraction(@PathVariable Long dealId,
                                                               @Valid @RequestBody CreateInteractionRequest request)
            throws NoAuthorizationException, EntityNotFoundException {
        return interactionService.addInteraction(dealId,request);
    }

    @GetMapping("/{dealId}")
    public BaseResponse<InteractionsResponse> getDealInteractions(@PathVariable Long dealId,
                                                          @RequestParam(name = "pageNo") int pageNo,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") int pageSize)
            throws NoAuthorizationException, EntityNotFoundException {
        return interactionService.getDealInteractions(dealId, pageNo, pageSize);
    }

    @PatchMapping("/{interactionId}")
    public BaseResponse updateInteraction(@PathVariable Long interactionId,
                                          @RequestBody UpdateInteractionRequest request)
            throws NoAuthorizationException, EntityNotFoundException {
        return interactionService.updateInteraction(interactionId, request);
    }

    @DeleteMapping("/{interactionId}")
    public BaseResponse deleteInteraction(@PathVariable Long interactionId)
            throws NoAuthorizationException, EntityNotFoundException {
        return interactionService.deleteInteraction(interactionId);
    }

}
