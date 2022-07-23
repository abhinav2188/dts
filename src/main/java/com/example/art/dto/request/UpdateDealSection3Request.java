package com.example.art.dto.request;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class UpdateDealSection3Request {

    private String siteLocation;

    private String cateredByVertical;

    private String paymentType;

    private Date openingDate;

    private Date expectedCloseDate;

    private Date actualCloseDate;

    private Integer expectedNumberOfDays;

    private String expectedDeliveryAddress;

    private String lastPurchaseDetails;

    private String competitorsInfo;

    private String remarks;

}
