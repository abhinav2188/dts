package com.example.art.dto.request;

import lombok.Data;

@Data
public class UpdateDealSection4Request {

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