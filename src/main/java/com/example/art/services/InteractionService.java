package com.example.art.services;

import com.example.art.dto.request.CreateInteractionRequest;
import com.example.art.dto.request.UpdateInteractionRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.InteractionsResponse;
import com.example.art.dto.response.inner.InteractionDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;

public interface InteractionService {

    BaseResponse<InteractionDetails> addInteraction(Long dealId, CreateInteractionRequest request) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse<InteractionsResponse> getDealInteractions(Long dealId, int pageNo, int pageSize) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse deleteInteraction(Long interactionId) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse updateInteraction(Long interactionId, UpdateInteractionRequest request) throws EntityNotFoundException, NoAuthorizationException;
}
