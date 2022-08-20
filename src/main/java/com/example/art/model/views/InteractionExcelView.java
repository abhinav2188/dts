package com.example.art.model.views;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class InteractionExcelView {

    private Long id;

    private Date createTimestamp;

    private Date updateTimestamp;

    private Date meetingDate;

    private String meetingLocation;

    private String contacts;

    private String consultants;

    private String handlers;

    private String meetingDetails;

    private Long dealId;

    private String dealName;

    private String dealStage;

}
