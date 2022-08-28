package com.example.art.services;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.DealHistoryResponse;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.enums.DealSubject;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DealHistoryService {

    void addDealHistory(Long dealId, DealSubject dealSubject, Object data);

    BaseResponse<DealHistoryResponse> getDealHistory(Long dealId, int pageNo, int pageSize) throws NoAuthorizationException, EntityNotFoundException;
}
