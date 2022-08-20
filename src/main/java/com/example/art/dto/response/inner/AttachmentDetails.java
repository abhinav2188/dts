package com.example.art.dto.response.inner;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class AttachmentDetails {

    private Date createTimestamp;

    private String id;

    private String documentName;

    private String documentType;

    private String path;

}
