package com.example.art.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class DropdownKeyValuesDetails {

    private String dropdownKey;

    private String formName;

    private List<DropdownValueDetails> values;

}
