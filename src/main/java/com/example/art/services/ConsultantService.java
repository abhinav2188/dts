package com.example.art.services;

import com.example.art.dto.request.CreateConsultantRequest;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.ConsultantsResponse;
import com.example.art.dto.response.SuccessDeleteResponse;
import com.example.art.dto.response.inner.ConsultantDetails;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;

public interface ConsultantService {

    BaseResponse<ConsultantDetails> addConsultant(Long dealId, CreateConsultantRequest request) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse<ConsultantsResponse> getDealConsultants(Long dealId, int pageNo, int pageSize) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse<SuccessDeleteResponse> deleteConsultant(Long consultantId) throws EntityNotFoundException, NoAuthorizationException;

}
