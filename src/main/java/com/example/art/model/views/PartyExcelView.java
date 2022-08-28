package com.example.art.model.views;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PartyExcelView {

    private Date createTimestamp;

    private Date updateTimestamp;

    private Long id;

    private String partyName;

    private String address;

    private String authority;

    private String mobile;

    private String email;

    private Boolean isActive;

}
