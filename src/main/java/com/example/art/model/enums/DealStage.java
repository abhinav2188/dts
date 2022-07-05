package com.example.art.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DealStage {

    OPPORTUNITY(1),
    ENQUIRY(2),
    QUOTATION(3),
    FOLLOW_UP(4),
    NEGOTIATION(5),
    WON(6.1),
    LOST(6.2),
    DROPPED(6.3)
    ;

    private double level;

}
