package com.example.art.controllers;

import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.DropdownKeyValuesResponse;
import com.example.art.exceptions.MissingUserRequestParamException;
import com.example.art.services.DropdownService;
import com.example.art.utils.Constants;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/ext/dropdown")
public class DropdownController {

    @Autowired
    private DropdownService dropdownService;

    @GetMapping
    public BaseResponse<DropdownKeyValuesResponse> getDropdownValues(@RequestParam(name = "dropdownType", required = false) String dropdownType ,
                                                                     @RequestParam(name = "formType", required = false) String formType,
                                                                     @RequestParam(name = "dealId", required = false) Long dealId)
            throws MissingUserRequestParamException {
        if(dealId != null) {
            MDC.put(Constants.DEAL_ID, String.valueOf(dealId));
        }
        return dropdownService.getDropdownValues(dropdownType, formType);

    }

}
