package com.example.art.dto.response;

import com.example.art.dto.response.inner.DropdownKeyValuesDetails;
import lombok.Data;

import java.util.Map;

@Data
public class DropdownKeyValuesResponse {

    Map<String, DropdownKeyValuesDetails> dropdownKeyDetailsMap;

}
