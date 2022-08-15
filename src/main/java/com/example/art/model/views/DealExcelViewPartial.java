package com.example.art.model.views;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DealExcelViewPartial {

    private Long id;

    private String productType;

    private String subCategoryProduct;

    private String unitOfQuantity;

    private String orderSizeFactor;

    private String typeOfWork;

    private String roadDetails;

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
