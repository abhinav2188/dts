package com.example.art.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DropdownValueDetails {

    private Long id;

    private String value;

    private Integer valueOrder;

}
