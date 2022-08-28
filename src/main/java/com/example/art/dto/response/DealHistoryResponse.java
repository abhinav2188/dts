package com.example.art.dto.response;

import com.example.art.model.DealHistory;
import lombok.Data;

import java.util.List;

@Data
public class DealHistoryResponse {

    private long totalCount;

    private int totalPageCount;

    private List<DealHistory> historyList;

}
