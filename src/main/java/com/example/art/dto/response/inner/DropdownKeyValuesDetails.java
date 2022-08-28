package com.example.art.dto.response.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DropdownKeyValuesDetails {

    private String dropdownKey;

    private String formName;

    private List<DropdownValueDetails> values;

}
