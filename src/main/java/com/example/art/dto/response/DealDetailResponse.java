package com.example.art.dto.response;

import com.example.art.dto.response.inner.*;
import lombok.Data;

@Data
public class DealDetailResponse {

    // for backend

    private DealCardDetails cardDetails;

    private DealProductDetails productDetails;

    private DealCommonDetails commonDetails;

    private DealAdditionalDetails additionalDetails;

    private DealAuthorizationDetails authorizationDetails;

//    private DealContactsDetails contactsDetails;
//
//    private DealConsultantsDetails consultantsDetails;
//
//    private DealInteractionsDetails interactionsDetails;

}
