package com.example.art.dto.response;

import com.example.art.dto.response.inner.DealCardDetails;
import lombok.Data;

import java.util.List;

@Data
public class MultipleDealsResponse {

    private long totalCount;

    private int totalPages;

    private List<DealCardDetails> deals;

}
