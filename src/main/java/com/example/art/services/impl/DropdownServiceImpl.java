package com.example.art.services.impl;

import com.example.art.dto.BaseResponse;
import com.example.art.dto.request.DropdownValueRequest;
import com.example.art.dto.response.*;
import com.example.art.helper.DropdownHelper;
import com.example.art.model.DropdownValue;
import com.example.art.model.enums.DropdownType;
import com.example.art.repository.DropdownRepository;
import com.example.art.services.DropdownService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DropdownServiceImpl implements DropdownService {

    @Autowired
    private DropdownRepository dropdownRepository;

    @Autowired
    private DropdownHelper dropdownHelper;

    @Override
    public BaseResponse addDropdownValue(DropdownValueRequest dto) {

        DropdownType dropdownType = dropdownHelper.getDropdownType(dto.getKey());
        if(dropdownType == null){
            return BaseResponse.builder()
                    .responseMsg("dropdown type not present")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        DropdownValue entity = getDropdownValueEntity(dto, dropdownType);

        entity = dropdownRepository.save(entity);

        String msg = "value '" + entity.getValue() + "' added to dropdown '" + entity.getDropdownType().name() + "'";

        return BaseResponse.builder()
                .status(HttpStatus.OK)
                .responseMsg(msg)
                .data(entity)
                .build();

    }

    @Override
    public BaseResponse<DropdownKeysResponse> getAllDropdownKeys() {

        DropdownKeysResponse response = new DropdownKeysResponse();
        response.setKeys(dropdownHelper.getDropdownKeyDetailsList());

        return BaseResponse.<DropdownKeysResponse>builder()
                .status(HttpStatus.OK)
                .data(response)
                .build();
    }

    @Override
    public BaseResponse<DropdownKeyValuesResponse> getAllDropdownValues() {

        DropdownKeyValuesResponse responseData = new DropdownKeyValuesResponse();

        Map<String, DropdownKeyValuesDetails> dropdownKeyDetailsMap = new HashMap<>();

        for(DropdownType dropdownType : DropdownType.values()){
            DropdownKeyValuesDetails dropdownKeyValuesDetails = getDropdownKeyValueDetails(dropdownType);
            if(dropdownKeyValuesDetails != null){
                dropdownKeyDetailsMap.put(dropdownType.name(), dropdownKeyValuesDetails);
            }
        }

        // todo : for static, dropdowns dealing with other entities like contact, user etc.

        responseData.setDropdownKeyDetailsMap(dropdownKeyDetailsMap);

        return BaseResponse.<DropdownKeyValuesResponse>builder()
                .status(HttpStatus.OK)
                .responseMsg("returned "+dropdownKeyDetailsMap.size()+" dropdown key, values")
                .data(responseData)
                .build();
    }

    @Override
    public BaseResponse deleteDropdownValue(Long valueId) {
        try{
            dropdownRepository.deleteById(valueId);
            return BaseResponse.builder()
                    .status(HttpStatus.OK)
                    .responseMsg("dropdown value deleted")
                    .build();
        }
        catch (EmptyResultDataAccessException e){
            log.error("error deleting dropdown value with id={}, error: {}",valueId,e.getMessage());
            return BaseResponse.builder()
                    .status(HttpStatus.OK)
                    .responseMsg("error deleting dropdown value, value doesn't exists")
                    .build();
        }
    }

    private DropdownKeyValuesDetails getDropdownKeyValueDetails(DropdownType dropdownType) {

        DropdownKeyValuesDetails dropdownKeyValuesDetails = new DropdownKeyValuesDetails();
        dropdownKeyValuesDetails.setDropdownKey(dropdownType.name());
        dropdownKeyValuesDetails.setFormName(dropdownType.getFormType().name());

        List<DropdownValueDetails> dropdownValueDetails = dropdownRepository.findAllByDropdownType(dropdownType);
//        if(dropdownValueDetails.size() == 0) return null;

        dropdownKeyValuesDetails.setValues(dropdownValueDetails);

        return dropdownKeyValuesDetails;

    }


    private DropdownValue getDropdownValueEntity(DropdownValueRequest dto, DropdownType dropdownType) {
        DropdownValue entity = new DropdownValue();
        entity.setDropdownType(dropdownType);
        entity.setValue(dto.getValue());
        entity.setValueOrder(dto.getOrder());
        return entity;
    }

}
