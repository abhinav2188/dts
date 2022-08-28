package com.example.art.model.views;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class PartyDropdownView {

    private Long id;

    private String partyName;

    private Date createTimestamp;

}
