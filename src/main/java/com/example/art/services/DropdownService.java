package com.example.art.services;

import com.example.art.dto.BaseResponse;
import com.example.art.dto.request.DropdownValueRequest;
import com.example.art.dto.response.DropdownKeyValuesResponse;
import com.example.art.dto.response.DropdownKeysResponse;
import com.example.art.exceptions.MissingUserRequestParamException;

public interface DropdownService {

    BaseResponse addDropdownValue(DropdownValueRequest dto);

    BaseResponse<DropdownKeysResponse> getAllDropdownKeys();

    BaseResponse<DropdownKeyValuesResponse> getAllDropdownValues();

    BaseResponse deleteDropdownValue(Long valueId);

    BaseResponse<DropdownKeyValuesResponse> getDropdownValues(String dropdownType, String formType) throws MissingUserRequestParamException;

}
