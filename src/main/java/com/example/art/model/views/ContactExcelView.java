package com.example.art.model.views;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ContactExcelView {

    private Long id;

    private Date createTimestamp;

    private Date updateTimestamp;

    private String name;

    private String email1;

    private String email2;

    private String mobile1;

    private String mobile2;

    private String designation;

    private String address;

    private Long dealId;

    private String dealName;

}
