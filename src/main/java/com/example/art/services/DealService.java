package com.example.art.services;

import com.example.art.dto.request.*;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.DealDetailResponse;
import com.example.art.dto.response.MultipleDealsResponse;
import com.example.art.dto.response.inner.DealCardDetails;
import com.example.art.dto.response.inner.DealUserDetails;
import com.example.art.exceptions.*;

public interface DealService {

    BaseResponse<DealCardDetails> createDeal(CreateDealRequest requestDto) throws InvalidFieldException, DuplicateEntryException;

    BaseResponse updateProductDetails(Long dealId, UpdateProductDetailsRequest requestDto) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse updateDealSection3(Long dealId, UpdateDealSection3Request requestDto) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse updateDealSection4(Long dealId, UpdateDealSection4Request requestDto) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse updateDealAuthorization(Long dealId, UpdateDealAuthorizationRequest request) throws EntityNotFoundException;

    BaseResponse<MultipleDealsResponse> getMultipleDeals(int pageNo, int pageSize);

    BaseResponse<DealDetailResponse> getDealDetails(Long dealId, Long userId) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse<DealDetailResponse> getDealDetails2(Long dealId, Long userId) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse<DealUserDetails> addDealOwner(Long dealId, String userEmail) throws EntityNotFoundException, NoAuthorizationException, InvalidOperationException;

    BaseResponse<DealUserDetails> removeDealOwner(Long dealId, String userEmail) throws NoAuthorizationException, EntityNotFoundException, InvalidOperationException;

    BaseResponse<MultipleDealsResponse> getMultipleDeals(int pageNo, int pageSize, String dealName, String partyName, String coOwnerEmail);

    BaseResponse<MultipleDealsResponse> getAllUserDeals(int pageNo, int pageSize, String dealName, String partyName, String coOwnerEmail);

}
