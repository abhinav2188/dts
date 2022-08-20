package com.example.art.dto.response.inner;

import lombok.Data;

import java.util.Date;

@Data
public class DealCommonDetails {

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
