package com.example.art.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DropdownType {

    AUTHORITY_TYPE(FormType.PARTY_DETAILS, false),

    PARTY(FormType.PARTY_DEAL, false),

    BROCHURES_TYPE(FormType.BROCHURES_QUERY, false),

    PRODUCT_TYPE(FormType.DEAL_PRODUCT_REQUIREMENTS, false),
    SUB_CATEGORY_PRODUCT(FormType.DEAL_PRODUCT_REQUIREMENTS, false),
    UNIT_OF_QUANTITY(FormType.DEAL_PRODUCT_REQUIREMENTS, false),
    TYPE_OF_WORK(FormType.DEAL_PRODUCT_REQUIREMENTS, false),

    VERTICAL(FormType.DEAL_DETAILS, false),
    PAYMENT_TYPE(FormType.DEAL_DETAILS, false),

    CONTACT_DESIGNATION(FormType.DEAL_CONTACTS, false),

    CONSULTANT_DESIGNATION(FormType.DEAL_CONSULTANTS, false),

    MEETING_PERSON(FormType.DEAL_INTERACTIONS, false),

    CURRENT_DEAL_STAGE(FormType.DEAL_ADDITIONAL, true),
    DEAL_OWNER(FormType.DEAL_ADDITIONAL, false),
    PAYMENT_TERMS(FormType.DEAL_ADDITIONAL, false),

    ;

    private FormType formType;
    private boolean isStatic;
}
