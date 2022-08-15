package com.example.art.model.views;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class ContactExcelView {

    private Long id;

    private Date createTimestamp;

    private Date updateTimestamp;

    private String name;

    private String email;

    private String mobile;

    private String designation;

    private Long dealId;

    private String dealName;

    public ContactExcelView(Long id, Date createTimestamp, Date updateTimestamp, String name, String email, String mobile, String designation, Long dealId, String dealName) {
        this.id = id;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.designation = designation;
        this.dealId = dealId;
        this.dealName = dealName;
    }
}
