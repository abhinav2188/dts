package com.example.art.dto.response.inner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DropdownKeyDetails {

    private String dropdownKey;

    private String formType;

    private boolean isStatic;

}
