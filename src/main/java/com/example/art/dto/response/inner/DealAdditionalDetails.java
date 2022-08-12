package com.example.art.dto.response.inner;

import lombok.Data;

@Data
public class DealAdditionalDetails {

    private String dealStage;

    private Boolean isActive;

    private Double dealValueInCr;

    private String paymentTerms;

    private Double paymentFactor;

    private Integer ownerFocus;

    private Double dealProbability;

    private String expectedTurnover;

    private String proximityFromBase;

}
