package com.example.art.dto.response.inner;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DealCardDetails {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="IST")
    private Date createTimestamp;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="IST")
    private Date updateTimestamp;

    private Long dealId;

    private String dealName;

    private String partyName;

    private Long partyId;

    private String dealStage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="IST")
    private Date openingDate;

    private Boolean isActive;

}
