package com.example.art.dto.response.inner;

import lombok.Data;

import java.util.Date;

@Data
public class DealCardDetails {

    private Date createTimestamp;

    private Date updateTimestamp;

    private Long dealId;

    private String dealName;

    private String partyName;

    private Long partyId;

    private String dealStage;

    private Date openingDate;

    private Boolean isActive;

}
