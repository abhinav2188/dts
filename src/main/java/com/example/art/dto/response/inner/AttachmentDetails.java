package com.example.art.dto.response.inner;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class AttachmentDetails {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="IST")
    private Date createTimestamp;

    private String id;

    private String documentName;

    private String documentType;

    private String path;

}
