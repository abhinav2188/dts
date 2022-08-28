package com.example.art.model.views;

import com.example.art.model.Deal;
import com.example.art.model.Party;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class DealExcelView {

    private Long id;

    private String dealName;

    private String partyName;

    private String coOwners;

    public DealExcelView(Long id, String dealName, String partyName, String coOwners) {
        this.id = id;
        this.dealName = dealName;
        this.partyName = partyName;
        this.coOwners = coOwners;
    }

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

