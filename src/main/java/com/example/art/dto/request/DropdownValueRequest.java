package com.example.art.dto.request;

import lombok.Data;

@Data
public class DropdownValueRequest {

    private String key;

    private String value;

    private Integer order;

}
