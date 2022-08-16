package com.example.art.services;

import com.example.art.model.enums.DealSubject;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DealHistoryService {

    void addDealHistory(Long dealId, DealSubject dealSubject, Object data);

}
