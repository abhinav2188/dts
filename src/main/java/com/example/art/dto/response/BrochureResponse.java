package com.example.art.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class BrochureResponse {

    private Date createTimestamp;

    private Date updateTimestamp;

    private String id;

    private String brochureName;

    private Boolean isActive;

    private String filePath;

}
