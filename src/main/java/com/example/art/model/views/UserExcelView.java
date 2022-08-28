package com.example.art.model.views;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserExcelView {

    private Date createTimestamp;

    private Date updateTimestamp;

    private Long id;

    private String email;

    private String mobile;

    private String designation;

    private Boolean isActive;

    private String roles;

}
