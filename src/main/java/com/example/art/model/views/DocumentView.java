package com.example.art.model.views;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DocumentView {

    private Date createTimestamp;

    private String id;

    private String documentName;

    private String documentType;

}
