package com.example.art.dto.response;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Data
public class BrochureResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="IST")
    private Date createTimestamp;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone="IST")
    private Date updateTimestamp;

    private String id;

    private String brochureName;

    private Boolean isActive;

    private String filePath;

}
