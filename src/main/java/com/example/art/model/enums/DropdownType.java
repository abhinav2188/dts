package com.example.art.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DropdownType {

    AUTHORITY_TYPE(FormType.PARTY_DETAILS),

    PARTY(FormType.PARTY_DEAL),

    BROCHURES_TYPE(FormType.BROCHURES_QUERY),

    PRODUCT_TYPE(FormType.DEAL_PRODUCT_REQUIREMENTS),
    SUB_CATEGORY_PRODUCT(FormType.DEAL_PRODUCT_REQUIREMENTS),
    UNIT_OF_QUANTITY(FormType.DEAL_PRODUCT_REQUIREMENTS),
    TYPE_OF_WORK(FormType.DEAL_PRODUCT_REQUIREMENTS),

    VERTICAL(FormType.DEAL_DETAILS),
    PAYMENT_TYPE(FormType.DEAL_DETAILS),

    CONTACT_DESIGNATION(FormType.DEAL_CONTACTS),

    CONSULTANT_DESIGNATION(FormType.DEAL_CONSULTANTS),

    MEETING_PERSON(FormType.DEAL_INTERACTIONS),

    CURRENT_DEAL_STAGE(FormType.DEAL_ADDITIONAL),
    DEAL_OWNER(FormType.DEAL_ADDITIONAL),
    PAYMENT_TERMS(FormType.DEAL_ADDITIONAL),

    ;

    private FormType formType;
}
