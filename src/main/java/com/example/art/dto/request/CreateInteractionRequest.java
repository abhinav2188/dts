package com.example.art.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class CreateInteractionRequest {

    private Date meetingDate;

    private String meetingLocation;

    private String meetingDetails;

    private String contacts;

    private String consultants;

    private String handlers;

}
