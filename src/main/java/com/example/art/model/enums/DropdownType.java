package com.example.art.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DropdownType {

    AUTHORITY_TYPE(FormType.PARTY_DETAILS, false, false),

    PARTY(FormType.PARTY_DEAL, false, true),

    BROCHURES_TYPE(FormType.BROCHURES_QUERY, false, true),
    DEAL_RECIPIENTS(FormType.BROCHURES_QUERY, false, true),

    PRODUCT_TYPE(FormType.DEAL_PRODUCT_REQUIREMENTS, false, false),
    SUB_CATEGORY_PRODUCT(FormType.DEAL_PRODUCT_REQUIREMENTS, false, false),
    UNIT_OF_QUANTITY(FormType.DEAL_PRODUCT_REQUIREMENTS, false, false),
    TYPE_OF_WORK(FormType.DEAL_PRODUCT_REQUIREMENTS, false, false),

    VERTICAL(FormType.DEAL_DETAILS, false, false),
    PAYMENT_TYPE(FormType.DEAL_DETAILS, false, false),

    CONTACT_DESIGNATION(FormType.DEAL_CONTACTS, false, false),

    CONSULTANT_DESIGNATION(FormType.DEAL_CONSULTANTS, false, false),

    MEETING_CONTACT(FormType.DEAL_INTERACTIONS, false, true),
    MEETING_CONSULTANT(FormType.DEAL_INTERACTIONS, false, true),
    MEETING_HANDLER(FormType.DEAL_INTERACTIONS, false, true),

    CURRENT_DEAL_STAGE(FormType.DEAL_ADDITIONAL, true, false),
    PAYMENT_TERMS(FormType.DEAL_ADDITIONAL, false, false),

    PARTY_NAME(FormType.DEAL_SEARCH, false, true),
    DEAL_NAME(FormType.DEAL_SEARCH, false, true),
    USER_EMAIL(FormType.DEAL_SEARCH, false, true),
//    DEAL_OWNER(FormType.DEAL_ADDITIONAL, false, true),

    ;

    private FormType formType;
    private boolean isStatic;
    private boolean isDerived;

}
