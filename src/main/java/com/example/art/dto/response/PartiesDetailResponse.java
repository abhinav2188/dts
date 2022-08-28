package com.example.art.dto.response;

import com.example.art.dto.response.inner.PartyDetails;
import lombok.Data;

import java.util.List;

@Data
public class PartiesDetailResponse {

    long totalCount;

    int totalPagesCount;

    List<PartyDetails> partyList;

}
