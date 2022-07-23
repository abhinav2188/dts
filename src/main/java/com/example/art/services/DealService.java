package com.example.art.services;

import com.example.art.dto.CreateDealResponse;
import com.example.art.dto.request.CreateDealRequest;
import com.example.art.dto.request.UpdateProductDetailsRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.InvalidFieldException;
import com.example.art.exceptions.NoAuthorizationException;

public interface DealService {

    BaseResponse<CreateDealResponse> createDeal(CreateDealRequest requestDto) throws InvalidFieldException, DuplicateEntryException;

    BaseResponse updateProductDetails(Long dealId, UpdateProductDetailsRequest requestDto) throws EntityNotFoundException, NoAuthorizationException;

}
