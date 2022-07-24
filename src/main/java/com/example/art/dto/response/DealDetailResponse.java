package com.example.art.dto.response;

import com.example.art.dto.response.inner.DealAdditionalDetails;
import com.example.art.dto.response.inner.DealAuthorizationDetails;
import com.example.art.dto.response.inner.DealCommonDetails;
import com.example.art.dto.response.inner.DealProductDetails;
import lombok.Data;

@Data
public class DealDetailResponse {

    // for backend

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
