package com.example.art.dto.response.inner;

import com.example.art.model.enums.DealStage;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class DealCardDetails {

    private Date createTimestamp;

    private Date updateTimestamp;

    private Long dealId;

    private String dealName;

    private String partyName;

    private Long partyId;

    private DealStage dealStage;

    private Date openingDate;

    private Boolean isActive;

}
