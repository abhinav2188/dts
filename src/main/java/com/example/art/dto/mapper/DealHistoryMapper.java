package com.example.art.dto.mapper;

import com.example.art.dto.response.DealHistoryResponse;
import com.example.art.model.DealHistory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class DealHistoryMapper {

    public DealHistoryResponse getDealHistoryResponse(Page<DealHistory> page) {
        DealHistoryResponse response = new DealHistoryResponse();
        response.setTotalCount(page.getTotalElements());
        response.setTotalPageCount(page.getTotalPages());
        response.setHistoryList(page.toList());
        return response;
    }

}
