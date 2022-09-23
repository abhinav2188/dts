package com.example.art.model.views;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DealCardView {

    private Long dealId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="IST")
    private Date createTimestamp;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="IST")
    private Date updateTimestamp;

    private String dealName;

    private String partyName;

    private Long partyId;

    private String dealStage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone="IST")
    private Date openingDate;

    private Boolean isActive;

    private String coOwners;

}
