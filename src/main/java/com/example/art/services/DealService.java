package com.example.art.services;

import com.example.art.dto.CreateDealResponse;
import com.example.art.dto.request.CreateDealRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.DuplicateEntryException;
import com.example.art.exceptions.InvalidFieldException;

public interface DealService {
    BaseResponse<CreateDealResponse> createDeal(CreateDealRequest requestDto) throws InvalidFieldException, DuplicateEntryException;
}
