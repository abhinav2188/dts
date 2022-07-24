package com.example.art.dto.response.inner;

import com.example.art.model.enums.DealStage;
import lombok.Data;

import java.util.Date;

@Data
public class InteractionDetails {

    private Long id;

    private Date createTimestamp;

    private Date meetingDate;

    private DealStage dealStage;

    private String meetingLocation;

    private String meetingDetails;

    private String contacts;

    private String consultants;

    private String handlers;

}
