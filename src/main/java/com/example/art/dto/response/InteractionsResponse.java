package com.example.art.dto.response;

import com.example.art.dto.response.inner.InteractionDetails;
import lombok.Data;

import java.util.List;

@Data
public class InteractionsResponse {

    private long totalCount;

    private int totalPageCount;

    private List<InteractionDetails> interactions;

}
