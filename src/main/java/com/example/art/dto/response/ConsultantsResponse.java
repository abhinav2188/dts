package com.example.art.dto.response;

import com.example.art.dto.response.inner.ConsultantDetails;
import lombok.Data;

import java.util.List;

@Data
public class ConsultantsResponse {

    private long totalCount;

    private int totalPageCount;

    private List<ConsultantDetails> consultants;

}
