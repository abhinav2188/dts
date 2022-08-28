package com.example.art.model.views;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ConsultantExcelView {

    private Long id;

    private Date createTimestamp;

    private Date updateTimestamp;

    private String name;

    private String email;

    private String mobile;

    private String designation;

    private Long dealId;

    private String dealName;

}
